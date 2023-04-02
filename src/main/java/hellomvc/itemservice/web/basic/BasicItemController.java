package hellomvc.itemservice.web.basic;

import hellomvc.itemservice.domain.item.Item;
import hellomvc.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
    //@Autowired
    //public BasicItemController(ItemRepository itemRepository){
    //    this.itemRepository = itemRepository;
    //}
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
       Item item= itemRepository.findById(itemId);
       model.addAttribute("item",item);
       return "basic/item";
    }
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
   // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        // model.addAttribute("item", item);
        // 이거 주석 해도 됨
        // @ModelAttribute 모델 객체도 만들고 자동으로 뷰에도 넣어줌! 이름은 "item"임!

        return "basic/item";
    }
    // @PostMapping("/add")
    public String addItemV3(Item item){
        itemRepository.save(item);
        return "basic/item";
    }
    @PostMapping("/add")
    public String addItemV4(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        // http://localhost:8080/basic/items/3?status=true
        // query파라미터로 넘어옴!
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item= itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item ) {
        itemRepository.update(itemId,item);
        // view템플릿을 호출하는 대신에 리다이렉트를 호출!!
        // controller에서 저 부분을 찾아서 가게 됨!
        return "redirect:/basic/items/{itemId}";
    }
    /**
     *  테스트용 데이터 추가!
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000,10));
        itemRepository.save(new Item("itemB", 70000,70));
    }





}
