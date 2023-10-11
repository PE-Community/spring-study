package pe.pecommunity.domain.post.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import pe.pecommunity.domain.post.domain.Post;

@Slf4j
public class PostSpecification {

    public static Specification<Post> searchPost(Map<String, String> searchKey){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(String key : searchKey.keySet()){
                if(key.equals("memberId")) {
                    predicates.add(criteriaBuilder.like(root.get("member").get(key), "%" + searchKey.get(key) + "%"));
                }
                else predicates.add(criteriaBuilder.like(root.get(key), "%" + searchKey.get(key) + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
