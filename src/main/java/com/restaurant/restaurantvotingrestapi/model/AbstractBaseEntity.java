package com.restaurant.restaurantvotingrestapi.model;

import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import javax.persistence.*;

@MappedSuperclass
//  https://stackoverflow.com/a/6084701/548473
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public abstract class AbstractBaseEntity implements Persistable<Integer>, HasId {

    public static final int START_SEQ = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_seq")
    @SequenceGenerator(name = "custom_seq", sequenceName = "custom_seq", allocationSize = 1, initialValue = AbstractBaseEntity.START_SEQ)
    //  See https://hibernate.atlassian.net/browse/HHH-3718 and https://hibernate.atlassian.net/browse/HHH-12034
    //  Proxy initialization when accessing its identifier managed now by JPA_PROXY_COMPLIANCE setting
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}