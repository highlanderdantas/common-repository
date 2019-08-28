package br.com.example.common;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
public class CommonRepository {

  @PersistenceContext
  private EntityManager manager;

  public Builder newBuilder() {
    return new CommonRepository.Builder().withManager(manager);
  }

  public @Getter @NoArgsConstructor static class Builder {

    private EntityManager manager;

    private Class<?> classQ;

    private Pageable pageable;

    private List<Parameter> parameters;

    private SimpleEntry<String, String> sort;

    private Predicate[] predicates;


    public Builder from(Class<?> classQ) {
      this.classQ = classQ;
      return this;
    }


    public Builder withPageable(Pageable pageable) {
      this.pageable = pageable;
      return this;
    }

    public Builder withManager(EntityManager manager) {
      this.manager = manager;
      return this;
    }

    public Builder withParameter(Parameter parameter) {
      if (this.parameters.size() == 0)
        this.parameters = new ArrayList<Parameter>();
      this.parameters.add(parameter);
      return this;
    }

    public Builder withParameter(String column, String operation, Object value) {
      Parameter parameter = new Parameter(value, operation, column);
      if (this.parameters == null || this.parameters.size() == 0)
        this.parameters = new ArrayList<Parameter>();
      this.parameters.add(parameter);
      return this;
    }

    public Builder withParameters(List<Parameter> parameters) {
      if (this.parameters.size() == 0)
        this.parameters = new ArrayList<Parameter>();
      this.parameters.addAll(parameters);
      return this;
    }

    public Builder withSort(SimpleEntry<String, String> sort) {
      this.sort = sort;
      return this;
    }

    public Builder withSort(String order, String column) {
      SimpleEntry<String, String> sort = new SimpleEntry<String, String>(order, column);
      this.sort = sort;
      return this;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toList() {
      Class<T> classOfT = (Class<T>) this.classQ;
      CriteriaBuilder builder = manager.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(classOfT);
      Root<T> root = criteria.from(classOfT);
      withPredicate(builder, root);
      criteria.where(this.predicates);
      TypedQuery<T> query = manager.createQuery(criteria);
      return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> T toOne() {
      Class<T> classOfT = (Class<T>) this.classQ;
      CriteriaBuilder builder = manager.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(classOfT);
      Root<T> root = criteria.from(classOfT);
      withPredicate(builder, root);
      criteria.where(this.predicates);
      TypedQuery<T> query = manager.createQuery(criteria);
      return query.getResultList().size() > 0 ? query.getSingleResult() : null;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> toPage() {
      Class<T> classOfT = (Class<T>) this.classQ;
      CriteriaBuilder builder = manager.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(classOfT);
      Root<T> root = criteria.from(classOfT);
      withPredicate(builder, root);
      criteria.where(this.predicates);
      TypedQuery<T> query = manager.createQuery(criteria);
      int totalElements = query.getResultList().size();
      addPagination(query);

      return new PageImpl<>(query.getResultList(), this.pageable, totalElements);
    }

    @SuppressWarnings("unchecked")
    public <T> Long toCount() {
      Class<T> classOfT = (Class<T>) this.classQ;
      CriteriaBuilder builder = manager.getCriteriaBuilder();
      CriteriaQuery<Long> criteria = (CriteriaQuery<Long>) builder.createQuery(classOfT);
      Root<T> root = criteria.from(classOfT);
      withPredicate(builder, root);
      criteria.where(this.predicates);
      criteria.select(builder.count(root));
      TypedQuery<Long> query = manager.createQuery(criteria);

      return query.getSingleResult();
    }

    private void addPagination(TypedQuery<?> query) {
      if (this.pageable == null)
        throw new IllegalStateException(
            "Não e possivel setar a paginação porque os valores dela não foram passados!");

      int currentPage = this.pageable.getPageNumber();
      int totalElements = this.pageable.getPageSize();
      int firstPageRegistration = currentPage * totalElements;

      query.setFirstResult(firstPageRegistration);
      query.setMaxResults(totalElements);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void withPredicate(CriteriaBuilder builder, Root root) {
      List<Predicate> predicatesArray = new ArrayList<Predicate>();

      for (Parameter parameter : this.parameters) {
        switch (parameter.getOperation()) {
          case EQ:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.equal(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case GT:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.greaterThan(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case LT:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.lessThan(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case GTE:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.greaterThanOrEqualTo(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case LTE:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.lessThanOrEqualTo(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case NE:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.notEqual(root.get(parameter.getColumn()),
                  (Comparable) parameter.getValueByType()));
            break;
          case LIKE:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              predicatesArray.add(builder.like((Expression<String>) root.get(parameter.getColumn()),
                  "%" + parameter.getValueByType() + "%"));
            break;
          case IN:
            if (parameter.getValue() != null
                && !StringUtils.isEmpty(parameter.getValue().toString()))
              searchAttributeIn(root, predicatesArray, parameter);
            break;

          default:
            break;
        }
      }
      if (sort != null) {
        if (sort.getKey().equals("desc")) {
          builder.desc(root.get(sort.getValue()));
        } else {
          builder.asc(root.get(sort.getValue()));
        }
      }
      predicates = predicatesArray.toArray(new Predicate[predicatesArray.size()]);

    }

    @SuppressWarnings("unchecked")
    private void searchAttributeIn(Root root, List<Predicate> predicatesArray,
        Parameter parameter) {
      Expression<Long> statusExpression = root.get(parameter.getColumn());
      String[] inStr = parameter.getValue().toString().split(",");
      if (isNumber(inStr[0])) {
        List<Long> in = new ArrayList<Long>();
        for (String valor : inStr) {
          in.add(Long.valueOf(valor));
        }
        predicatesArray.add(statusExpression.in(in));
      } else {
        predicatesArray.add(statusExpression.in(inStr));
      }
    }

    private static Boolean isNumber(String s) {
      boolean isNumber = true;
      for (int i = 0; i < s.length(); i++) {
        if (!Character.isDigit(s.charAt(i))) {
          isNumber = false;
          break;
        }
      }
      return isNumber;
    }

  }

}
