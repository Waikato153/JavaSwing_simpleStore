package ictgradschool.industry.final_project.model;

import ictgradschool.industry.final_project.model.bean.ShoppingItem;

import java.io.*;
import java.util.*;

/**
 * Class to represent a collection of ShoppingItem instances.
 * 
 */
public class ShoppingCartList {

    /*
     * An indexed collection of the ShoppingItem objects. This essentially provides an index offering direct access to
     * any ShoppingItem object stored.
     */
    private List<ShoppingItem> _indexedResults;

    private Set<ShoppingCartListener> _listeners ;

    private HashSet<ShoppingCartObserver> _observers = new HashSet<>();

    /*
     * Creates an empty Course instance.
     */
    public ShoppingCartList() {
        _indexedResults = new ArrayList<ShoppingItem>();
        _listeners = new HashSet<>();
    }
    /**
     * Adds a new ShoppingItem object to the model.
     *
     * @param result
     *            the new ShoppingItem object.
     */
    public void addShoppingCartResult(ShoppingItem result) {
        if (size() == 0) {
            _indexedResults.add(result);
        } else {
            ShoppingItem last = this.getResultAt(size()-1);
            if (last.getProductId() == result.getProductId()) {
                this.getResultAt(size()-1).setQuantity(last.getQuantity() + result.getQuantity());
            } else {
                _indexedResults.add(result);
            }
        }
        for(ShoppingCartListener listener : _listeners) {
            listener.CartDataChanged(this);
        }
    }
    public void removeShoppingItem (int index) {
        ShoppingItem item = _indexedResults.get(index);
        _indexedResults.remove(index);
        for(ShoppingCartListener listener : _listeners) {
            listener.CartDataChanged(this);
        }
    }
    /**
     * Returns the ShoppingItem object at a specified index position within the
     * model.
     *
     * @param index
     *            the index position.
     * @return the corresponding ShoppingItem object or null if there is no
     *         matching ShoppingItem object in the model.
     */
    public ShoppingItem getResultAt(int index) {
        if (index < 0 || index >= _indexedResults.size()) {
            return null;
        } else {
            return _indexedResults.get(index);
        }
    }

    /**
     * Returns the index of a particular ShoppingItem object.
     *
     * @param result
     *            the ShoppingItem object whose index position is sought.
     * @return the index position.
     */
    public int indexOf(ShoppingItem result) {
        return _indexedResults.indexOf(result);
    }

    /**
     * Returns an Iterator object which can be used to iterate through the
     * Course model's ShoppingItem objects.
     *
     * @return an Iterator of ShoppingItem objects.
     */
    public Iterator<ShoppingItem> iterator() {
        return _indexedResults.iterator();
    }
    /**
     * Returns the number of ShoppingItem objects stored in the model.
     */
    public int size() {
        return _indexedResults.size();
    }


    public void clear() {
        _indexedResults.clear();
        for(ShoppingCartListener listener : _listeners) {
            listener.CartDataChanged(this);
        }
    }

    public double  getTotalPrice() {
        double total = 0;
        for (ShoppingItem item : _indexedResults) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return Double.valueOf(String.format("%.2f", total));
    }
    public void addListener(ShoppingCartListener listener) {
        _listeners.add(listener);
    }
    public Set getListeners() {
        return _listeners;
    }
    public void removeListener(ShoppingCartListener listener) {
        _listeners.remove(listener);
    }
    public void addObserver(ShoppingCartObserver observer) {
        _observers.add(observer);
    }
    public Set<ShoppingCartObserver> listObserver() {
        return _observers;
    }

    public void writeFile(String fileName) {
        String receiptContent = generateReceipt();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(receiptContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateReceipt() {
        StringBuilder receiptBuilder = new StringBuilder();

        receiptBuilder.append("------------------------------------------------------\n");

        List<ShoppingItem> combineResult = new ArrayList<>();

        int priceLenth = 0;

        for (ShoppingItem item : _indexedResults) {
            if (combineResult.size() == 0) {
                combineResult.add(item);
                priceLenth = String.valueOf(item.getProduct().getPrice()).length();
            } else {
                boolean isExist = false;
                for (ShoppingItem combineItem : combineResult) {
                    if (combineItem.getProductId() == item.getProductId()) {
                        combineItem.setQuantity(combineItem.getQuantity() + item.getQuantity());
                        isExist = true;
                    }
                }
                if (!isExist) {
                    int newLength = String.valueOf(item.getProduct().getPrice()).length();
                    priceLenth = newLength > priceLenth ? newLength : priceLenth;
                    combineResult.add(item);
                }
            }
        }


        for (ShoppingItem product : combineResult) {
            List<String> wrappedLines = wrapText(product.getProduct().getName(), 15);
            for (int i = 0; i < wrappedLines.size(); i++) {
                if (i == 0) {
                    int priceSpace = priceLenth - String.valueOf(product.getProduct().getPrice()).length();
                    receiptBuilder.append(String.format("%-5s  %-20s  ($%-2s)  %-2s  %-2s%n", String.valueOf(product.getQuantity()), wrappedLines.get(i), product.getProduct().getPrice(),"", " ".repeat(priceSpace)+ "$" + product.getProduct().getPrice() * product.getQuantity()));
                } else {
                    receiptBuilder.append(String.format("%-5s  %-20s%n", "", wrappedLines.get(i)));
                }
            }
        }

        receiptBuilder.append("======================================================\n");

        receiptBuilder.append(String.format("%-5s  %-20s  %-7s %-5s  $%-2s\n", "", "TOTAL", "", "", getTotalPrice()));
        receiptBuilder.append("------------------------------------------------------");
        return receiptBuilder.toString();
    }
    private static List<String> wrapText(String text, int lineLength) {
        List<String> wrappedLines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() <= lineLength) {
                currentLine.append(word).append(" ");
            } else {
                wrappedLines.add(currentLine.toString());
                currentLine = new StringBuilder(word).append(" ");
            }
        }

        if (currentLine.length() > 0) {
            wrappedLines.add(currentLine.toString());
        }
        return wrappedLines;
    }

}
