package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
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
/**@RequiredArgsConstructor
 * final이 붙은 변수의 생성자코드를 만들어줌.
 *
 * public BasicItemController(ItemRepository itemRepository) {
 *         this.itemRepository = itemRepository;
 *     }
 */
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

//@PostMapping("/add")
//public String addItemV2(@ModelAttribute("item") Item item, Model model) {
////    ModelAttribute는 Item 객체생성 및 생성한객체를 Model에 담아준다.
////    그래서 addAttribute 생략가능.
//
//    itemRepository.save(item);
////    model.addAttribute("item", item);
//
//    return "basic/item";
//}

//@PostMapping("/add")
//public String addItemV3(@ModelAttribute Item item) {
//    //이름 생략시  Item -> item 바꿔서 모델에 담아준다.
//    itemRepository.save(item);
////    model.addAttribute("item", item);
//
//    return "basic/item";
//}

//    @PostMapping("/add")
//    public String addItemV4(Item item) {// @ModelAttribute 생략가능
//        itemRepository.save(item);
//        Long itemId = item.getId();
//        return "redirect:/basic/items/" + itemId;
//    }

    /**
     * RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);//return 에 명시안한 status는 ?status=true 쿼리파라미터 추가
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
            Item item = itemRepository.findById(itemId);
            model.addAttribute("item", item);
            return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }


    /**
     * test data
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
