package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
         //저장된 갯수가 일치하는지
        assertThat(result.size()).isEqualTo(2);
            //contains - 안에 객체가 들어있는지 확인
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item saveItem = itemRepository.save(item1);
        Long itemId = saveItem.getId();
        //when
            //update
        Item item2 = new Item("itemRe",20000,20);
        itemRepository.update(itemId,item2);

        //then
        Item findItem =itemRepository.findById(itemId);
        assertThat(findItem.getId()).isEqualTo(saveItem.getId());
        assertThat(findItem.getItemName()).isEqualTo(saveItem.getItemName());

    }
}