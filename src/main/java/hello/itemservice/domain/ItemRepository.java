package hello.itemservice.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    //HashMap 동시 여러 쓰래드 접근 불가 - > ConcurrentHashMap 사용해야한다.
    private static final Map<Long,Item> store= new HashMap<>();//static
    private static Long sequence = 0L; //static

    //저장
    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    // 전체 리스트
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    //수정
    public Item update(long itemId, Item updateParam){
        Item findItem = findById(itemId);
        // 파라미터 정보로 재 설정
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());

        return findItem;
    }

    //테스트용 리스트 제거
    public void clearStore(){
        store.clear();
    }

}
