package student.inti.gg;

public class Model {
    String Brand,Image,Name;
    String Category;
    String Description;
    String Price;
    String URL;
    String Website;
    String Imageview;
    String Id;


    public Model(){

    }

    public Model(String brand, String image, String name, String category, String description, String price, String URL, String website,
                 String imageview, String id) {
        Brand = brand;
        Image = image;
        Name = name;
        Category = category;
        Description = description;
        Price = price;
        this.URL = URL;
        Website = website;
        Imageview = imageview;
        Id = id;

    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }


    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageview() {
        return Imageview;
    }

    public void setImageview(String imageview) {
        Imageview = imageview;
    }
}
