package nce.majorproject.recommendation.constants;

public enum TypeOfInput {
    VIEW(1),
    CART(3),
    CHECKOUT(4);
    private int val;
    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    TypeOfInput(int val) {
        this.val = val;
    }
}
