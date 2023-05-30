package ictgradschool.industry.final_project.admin;


import ictgradschool.industry.final_project.model.Product;

/**
 * <p>Simple Builder pattern implementation to create a ProductResult object from a series of values.</p>
 *
 * <p>A Builder pattern allows us to defer creation of an object until all necessary instantiation/initialization
 * values have been collected, and maintains an internal representation of these values. This encapsulation of
 * values allows users of the Builder to provide values to the Builder that will be used for later creation of
 * the desired object, encapsulating the values to avoid maintaining temporary variables in other classes.
 * Creation of the desired object is delayed until all necessary values have been provided - supporting defaults
 * for values where appropriate. Users of the Builder can provide values as they become available, effectively
 * allowing a "partial" creation of an object that is only realised once all values are provided.</p>
 *
 * <p>A Builder is capable of simplifying the construction and initialization of objects, masking complexity
 * of wrapping values in additional data structures, providing defaults for values where appropriate, and
 * removing long opaque constructor calls from code.</p>
 *
 * <p>When using a Builder, typical usage may look like the following example:</p>
 *
 * <pre>Builder b = new Builder();
 * b.setValueA("foo"); // Set a value
 * b.setValueB("bar"); // Set a value
 *
 * // other processing to acquire further values
 *
 * b.setValueC("baz"); // Set a value
 *
 * // When ready for the object to be created
 * Object realised = b.getObject();</pre>
 *
 * <p>Builder setters conventionally return a reference to <code>this</code>, which allows for chaining setters in a compact manner:</p>
 *
 * <pre>Builder b = new Builder() // create the initial builder
 *          .setValueA("foo")  // set value, returning the builder
 *          .setValueB("bar")  // set value, returning the builder
 *          .setValueC("baz"); // set value, returning the builder
 *
 * Object realised = b.setValueD("another") // set value, returning the builder
 *          .getObject(); // Realise the object</pre>
 */
public class ProductResultBuilder {
    /*
     * Each value to be passed to the ProjectResult constructor is stored internally.
     * Setter methods will mutate their associated value, and getProjectResult() will
     * use these values when constructing a ProductResult.
     */

    //unique id 10-characters
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;

    /**
     * Construct a new instance of ProductResultBuilder, with fields initialised to defaults
     */
    public ProductResultBuilder() {
        this.reset();
    }

    /**
     * Reset the member variables of this ProductResultBuilder instance to default values. This allows for reuse of this instance.
     */
    public void reset() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = 0.0;
        this.quantity = 0;
    }

    /**
     * <p>Set the <code>id</code> field for a ProductResult</p>
     *
     * @param id ID number of the product
     * @return ProductResultBuilder reference for chaining calls
     */
    public ProductResultBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * <p>Set the <code>name</code> field for a ProductResult</p>
     *
     * @param name name of the product
     * @return ProductResultBuilder reference for chaining calls
     * @throws BuilderException When <code>name</code> is <code>null</code>.
     */
    public ProductResultBuilder name(String name) throws BuilderException {
        if (name == null) {
            throw new BuilderException("ProductResultBuilder.surname :: value for name cannot be null");
        }

        this.name = name;
        return this;
    }

    /**
     * <p>Set the <code>description</code> field for a ProductResult</p>
     *
     * @param description description of the product
     * @return ProductResultBuilder reference for chaining calls
     * @throws BuilderException When <code>description</code> is <code>null</code>.
     */
    public ProductResultBuilder description(String description) throws BuilderException {
        if (description == null) {
            throw new BuilderException("ProductResultBuilder.description :: value for description cannot be null");
        }
        this.description = description;
        return this;
    }

    /**
     * <p>Set the <code>price</code> field for a ProductResult</p>
     *
     * @param price price for the product
     * @return ProductResultBuilder reference for chaining calls
     * @throws BuilderException When <code>price</code>.
     */
    public ProductResultBuilder price(double price) throws BuilderException {
        if (price < 0) {
            throw new BuilderException(String.format("ProductResultBuilder.price :: value for price is outside of bounds 0<=price. Provided: %d", price));
        }

        this.price = price;
        return this;
    }

    /**
     * <p>Set the <code>quantity</code> field for a ProductResult</p>
     *
     * @param quantity quantity for the product
     * @return ProductResultBuilder reference for chaining calls
     * @throws BuilderException When <code>quantity</code> is outside of range <code>0 <= quantity</code>.
     */
    public ProductResultBuilder quantity(int quantity) throws BuilderException {
        if (quantity < 0) {
            throw new BuilderException(String.format("ProductResultBuilder.quantity :: value for quantity is outside of bounds 0<=mark. Provided: %d", quantity));
        }

        this.quantity = quantity;
        return this;
    }

    /**
     * <p>Create and return a new ProductResult object initialised with the values provided to this
     * ProductResultBuilder instance. The ProductResultBuilder can optionally be reset to allow for
     * reuse of the Builder instance.</p>
     *
     * @param shouldReset If <code>true</code>, reset this ProductResultBuilder so that it can be reused
     * @return A ProductResult object initialised with the values provided to this ProductResultBuilder
     * @throws BuilderException If surname or forename have not been provided
     */
    public Product getProductResult(boolean shouldReset) throws BuilderException {
        if (this.name == null) {
            throw new BuilderException("ProductResultBuilder.getProductResult :: unable to construct ProductResult with null name");
        }
        // Complexity of wrapping mark values in a Percentage object is masked by the Builder
        Product sr = new Product(this.id, this.name, this.description, this.price, this.quantity);

        if (shouldReset) { this.reset(); }

        return sr;
    }

    /**
     * <p>Create and return a new ProductResult object initialised with the values provided to this
     * ProductResultBuilder instance, resetting the ProductResultBuilder following ProductResult
     * object creation.</p>
     *
     * @return A ProductResult object initialised with the values provided to this ProductResultBuilder
     * @throws BuilderException If surname or forename have not been provided
     */
    public Product getProductResult() throws BuilderException {
        return this.getProductResult(true);
    }
}
