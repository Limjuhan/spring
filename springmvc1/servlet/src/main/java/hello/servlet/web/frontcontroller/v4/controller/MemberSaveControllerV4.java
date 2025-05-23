package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String unsername = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(unsername, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
