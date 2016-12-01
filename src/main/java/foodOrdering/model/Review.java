package foodOrdering.model;

public class Review {

    int rating;
    String review;
    int customerId;
    String customerName;

    Review(){

    }

    public Review(int rating, String review, int customerId, String customerName) {
        this.rating = rating;
        this.review = review;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
