package pe.pecommunity.domain.member.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.pecommunity.domain.comment.dao.CommentRepositoryCustom;
import pe.pecommunity.domain.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, CommentRepositoryCustom {
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
}
