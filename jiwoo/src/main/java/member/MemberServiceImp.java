package member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        validateDuplicateId(member);
        memberRepository.save(member);
    }

    @Override
    public Member login(String id, String pw) {
        return memberRepository.findByMemberId(id)
                .filter(m->m.getPw().equals(pw)).orElse(null);
    }

    public void validateDuplicateId(Member member) {
        Optional<Member> findMemberId = memberRepository.findByMemberId(member.getId());
        if (findMemberId != null) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
}
