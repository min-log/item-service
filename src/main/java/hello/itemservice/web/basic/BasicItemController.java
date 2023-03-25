package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }


    //저장 용
    //ver. 1
//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName
//            ,@RequestParam int price
//            ,@RequestParam Integer quantity
//            ,Model model
//    ){
//        Item item = new Item(itemName,price,quantity);
//
//        Item saveItem = itemRepository.save(item);
//        model.addAttribute("item",saveItem);
//        return "basic/item";
//    }

    //ver. 2
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item
////            ,Model model
//    ){
//        Item saveItem = itemRepository.save(item);
//        //      model.addAttribute("item",saveItem);
//        return "basic/item";
//    }


    //ver. 3
//    @PostMapping("/add")
//    public String addItemV3(Item item){
//        itemRepository.save(item);
//        // return "basic/item";  -- 새로고침 시 의도하지 않은 저장이 한번더 이루어질 수 있다. 서버에 요청의 마지막은 post였기때문.
//
//        //경로직접 입력 하여 새로 페이지 링크를 전달한다. -- 하지만 바로 전달 시 url에 한글이 들어가거나 하면 인코딩 문제가 있을 수 있다.
//        return "redirect:/basic/items/"+item.getId();
//    }


    @PostMapping("/add")
    public String addItemV3(Item item , RedirectAttributes redirectAttributes,Model model){ // RedirectAttributes
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id",saveItem.getId()); //리다이렉트 uri 바로 사용
        redirectAttributes.addAttribute("status",true);//성공 값 --쿼리파라미터 값으로 전달 ?status=true
        redirectAttributes.addAttribute("new",true);

        //경로직접 입력 하여 새로 페이지 링크를 전달한다. -- 하지만 바로 전달 시 url에 한글이 들어가거나 하면 인코딩 문제가 있을 수 있다.
        return "redirect:/basic/items/{id}";
    }



    //상품수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        return "basic/editForm";
    }



    //상품수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId,
                       @ModelAttribute("item") Item item,
                       RedirectAttributes redirectAttributes){

        Item updateItem = itemRepository.update(itemId, item);
        redirectAttributes.addAttribute("status",true);//성공 값 --쿼리파라미터 값으로 전달 ?status=true
        redirectAttributes.addAttribute("re",true);
        return "redirect:/basic/items/{itemId}";
    }




    /*
    * 테스트용 데이터
    * */
    @PostConstruct
    public void init(){
        Item itemA = new Item("item1",20000,20);
        Item itemB = new Item("item2",25000,10);
        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }
}
