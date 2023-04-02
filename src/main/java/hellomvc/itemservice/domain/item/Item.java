package hellomvc.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price; //가격이 안정해졌을때는 널
    private Integer quantity;

    public Item() {
    }
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
