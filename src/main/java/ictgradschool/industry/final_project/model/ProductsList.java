package ictgradschool.industry.final_project.model;

import ictgradschool.industry.final_project.admin.ProductResultBuilder;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.model.bean.ShoppingItem;
import ictgradschool.industry.final_project.model.worker.SaveWorker;
import ictgradschool.industry.final_project.util.productAction;

import java.io.*;
import java.util.*;

public class ProductsList {
    // Name of data file storing ProductResult data.
    private static String DATA_FILE_NAME = "results.dat";

    // Input file within resources directory
    private static String INPUT_FILE_NAME = "";

    //private static List<Product> products;
    protected static HashMap<String, Product> products;

    /*
     * An indexed collection of the Product objects stored in the results
     * hashtable. This essentially provides an index offering direct access to
     * any StudentResult object stored.
     */
    protected List<Product> _indexedResults;
    protected List<Product> _cartResults;
    protected static List<String> productIds = new ArrayList<>();
    protected Set<ProductsListListener> listeners ;

    private HashSet<String> _selectedIds = new HashSet<>();


    public ProductsList() {
        products = new HashMap<String, Product>();
        listeners = new HashSet<>();
        _indexedResults = new ArrayList<>();
        _cartResults = new ArrayList<>();
    }

    public void add(Product product) {
        products.put(product.getId(), product);
        productIds.add(product.getId());
        _indexedResults = new ArrayList<>(products.values());
        Collections.sort(_indexedResults, (a, b) -> Integer.valueOf(b.getPrimarykey()).compareTo(Integer.valueOf(a.getPrimarykey())));

        for(ProductsListListener listener : listeners) {
            listener.projectDataAdded(this);
        }
    }

    public void clear() {
        products.clear();
        productIds.clear();
        //_indexedResults.clear();
        //_cartResults.clear();
        //listeners.clear();
    }

    public void batchDelete() {
        for (String element : _selectedIds) {
            products.remove(element);
            productIds.remove(element);
        }
        if (_selectedIds.size() > 0) {
            Iterator<Product> iterator = _indexedResults.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (_selectedIds.contains(product.getId())) {
                    iterator.remove();
                }
            }
            clearSelectedIds();
        }

        for (ProductsListListener l : listeners) {
            l.projectDataRemoved(this);
        }
    }
    public void batchAddCart(ShoppingCartList shoppingCartList) {

        this.getSelectedIds().forEach(id -> {
            Product item = this.getProductById(id);
            item.setQuantity(item.getQuantity() - 1);
            ShoppingItem shoppingItem = new ShoppingItem(id, 1, item);

            Iterator<Product> iterator = _indexedResults.iterator();
            if (item.getQuantity() <= 0) {
                products.remove(id);
                productIds.remove(id);
                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    if (id.equals(product.getId())) {
                        iterator.remove();
                    }
                }
                removeSelect(id);
            }
            for (ProductsListListener l : listeners) {
                l.projectDataRemoved(this);
            }
            shoppingCartList.addShoppingCartResult(shoppingItem);
        });
    }


    public void addSelect(String id) {
        _selectedIds.add(id);
    }

    public void removeSelect(String id) {
        _selectedIds.remove(id);
    }

    public HashSet<String> getSelectedIds() {
        return _selectedIds;
    }
    public Product getProductById(String id) {
        return products.get(id);
    }

    public void clearSelectedIds() {
        _selectedIds.clear();
    }



    public void addListener(ProductsListListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ProductsListListener listener) {
        listeners.remove(listener);
    }

    public int size() {
        return _indexedResults.size();
    }

    public Product get(int index) {
        return _indexedResults.get(index);
    }


    public static void setData_file_name(String data_file_name) {
        INPUT_FILE_NAME = data_file_name;
    }

    public void setTxtFile(String txtFile) {
        this.INPUT_FILE_NAME = txtFile;
    }

    public String getTxtFile() {
        return INPUT_FILE_NAME;
    }

    public static List<String> getProductIds() {
        return productIds;
    }
    /**
     * Attempts to read in an input CSV of data, converting each row into a StudentResult object.
     * Generated objects are then serialized into an output file for later consumption.
     */
    public static void generateData() {
        List<Product> results = new ArrayList<>();
        // Open the input CSV file for processing
        try (Scanner sc = new Scanner(new File(ProductsList.INPUT_FILE_NAME))) {

            sc.useDelimiter(",|\n|\r\n");

            // Create a builder instance to assist with creating StudentResult objects from a stream of data
            ProductResultBuilder srb = new ProductResultBuilder();

            // Read each CSV row
            while (sc.hasNext()) {
                String id = sc.next();
                if (id.isBlank() || id.isEmpty()) {
                    break;
                }

                // Use a Builder pattern to generate a StudentResult object from data as it is read
                results.add(
                        srb.id(id)
                                .name(sc.next())
                                .description(sc.next())
                                .price(sc.nextDouble())
                                .quantity(sc.nextInt())
                                .primarykey(sc.nextInt())
                                .getProductResult(true));
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Unable to read input file \"%s\"\n", ProductsList.INPUT_FILE_NAME);
            e.printStackTrace();
        }

        // Open an output stream, and serialize each StudentResult object to the stream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))) {
            // Write out the number of entries to ease reading
            oos.writeInt(results.size());

            for (Product result : results) {
                oos.writeObject(result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Attempts to deserialise a List of ProjectResult objects from disk.
     */
    public static List<Product> readData(boolean needZero) {
        List<Product> csvProducts = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE_NAME))) {
            // Read the number of entries the stream contains
            int count = ois.readInt();


            // Attempt to read each object from the stream
            for (int i = 0; i < count; i++) {
                Product p = (Product) ois.readObject();
                if (needZero == false && p.getQuantity() == 0) {
                    continue;
                }
                csvProducts.add(p);
            }
            System.out.println("Reading " + csvProducts.size() + " products records.");
            ois.close();
        } catch (IOException e) {
            /*
             * An IOException will be thrown if an error is encountered when
             * reading from the data file.
             */
            System.out.println("Error reading from data file: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Deserialization of object failed, no matching class found.");
        }
        return csvProducts;
    }

    public void saveFile(Boolean append, String pid) {
        System.out.println("Saving to file...");
        System.out.println("Append: " + append);
        System.out.println("PID: " + pid);
        System.out.println("Products: " + this.products);
        Map<String, Product> data = new HashMap<>();
        if (append == true && pid != null) {
            data.put(pid, this.products.get(pid));
        } else {
            data = this.products;
        }
        writeFile(data, append);
    }
    private void writeFile(Map<String, Product> data, Boolean append) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(INPUT_FILE_NAME, append))) {
            if (data.size() == 0) {
                out.write("".getBytes());
            } else {
                for (String key : data.keySet()) {
                    Product product = data.get(key);
                    StringBuffer sbf = new StringBuffer();//拼接内容
                    sbf.append(product.getId()).append(",");
                    sbf.append(product.getName()).append(",");
                    sbf.append(product.getDescription()).append(",");
                    sbf.append(product.getPrice()).append(",");
                    sbf.append(product.getQuantity()).append(",");
                    sbf.append(product.getPrimarykey());
                    sbf.append(System.lineSeparator());
                    String str = sbf.toString();
                    byte[] b = str.getBytes();
                    for (int i = 0; i < b.length; i++) {
                        out.write(b[i]);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void triggerSave() {
        SaveWorker saveWorker = new SaveWorker(this, productAction.Edit,null);
        saveWorker.execute();
    }
    public void updateFile(ShoppingCartList shoppingCartList) {
        List<Product> allProduct = readData(true);
        for (Product product : allProduct) {
            for (int i = 0; i < shoppingCartList.size(); i++) {
                if (product.getId().equals(shoppingCartList.getResultAt(i).getProductId())) {
                    product.setQuantity(product.getQuantity() - shoppingCartList.getResultAt(i).getQuantity());
                }
            }
        }
        HashMap<String, Product> hashMap = new HashMap<>();
        for (int i = 0; i < allProduct.size(); i++) {
            hashMap.put(allProduct.get(i).getId(), allProduct.get(i));
        }
        writeFile(hashMap, false);
    }
}