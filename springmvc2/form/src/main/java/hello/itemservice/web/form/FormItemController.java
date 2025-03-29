package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @PathVariable: URL 경로에서 변수 값을 추출(ex: @RequestParam String keyword)
 *
 * @RequestParam: 쿼리 파라미터를 메서드 파라미터로 받음( URL의 ?key=value 형태). 보통 DTO없이 param받을때 사용
 *
 * @ModelAttribute: 폼 데이터(www-form-urlencoded)나 쿼리 파라미터를 Java 객체로 받음. 사전에 반드시 DTO생성 필수.
 *
 * @RequestBody: 요청 본문을 객체로 변환하여 받음(JSON, XML)
 *
 * @RequestHeader: 요청 헤더에서 값을 추출. 주로 인증 관련 헤더(Authorization, Content-Type 등)
 *
 * @CookieValue: 쿠키에서 값을 추출
 *
 * @RequestPart: 멀티파트 요청에서 파일을 받음. (RequestPart("file") MultipartFile file)
 *
 * @RequestMapping: HTTP 메서드를 지정하지 않고 모든 요청을 처리
 *
 * @ResponseBody: 응답 본문으로 데이터를 반환
 */
@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

//    컨트롤러 호출시 "regions"는 항상 모델에 담는다.
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String>regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");

        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
//        enum클래스 값들 배열로 리턴.
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));

        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
//        html에서 타임리프 활용을 위해 빈객체 생성후 넘기기
        model.addAttribute("item", new Item());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.type={}", item.getItemType());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

