package hellomvc.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    // 멀티 쓰레드 환경에서는 해쉬맵 원래는 절대 쓰면 안됨!
    // ConcurrentHashMap을 사용해야함!
    // 아래 Long도 마찬가지로 동시 접근시 값이 꼬일수가 있음!
    private static final Map<Long, Item> store = new HashMap<>();

    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long itemId){
        return store.get(itemId);
    }
    public List<Item> findAll() {
        // ArrayList에 값이 삽입되거나 수정되도 store에는 문제가 없다!
        return new ArrayList<>(store.values());
    }
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }

}
