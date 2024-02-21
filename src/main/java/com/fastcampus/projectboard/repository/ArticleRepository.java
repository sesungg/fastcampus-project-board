package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,     // 기본 검색 기능
        QuerydslBinderCustomizer<QArticle>      // 세부 검색 기능
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // 기본 검색 기능에 의해 엔티티에 있는 모든 필드에 대한 검색이 되고있으나, 원하는 필드만 설정하기 위해 false(기본값) -> true로 변경
        bindings.excludeUnlistedProperties(true);
        // 검색을 원하는 필드 추가
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        // 대소문자 구분을 하지 않으며 like 검색
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        // 대소문자 구분을 하지 않으며 like 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        // 대소문자 구분을 하지 않으며 like 검색
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        // 마땅한 검색기능이 없기때문에 단순 equals 검색 (년월일시분초까지 정확해야 검색이 가능)
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        // 대소문자 구분을 하지 않으며 like 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}