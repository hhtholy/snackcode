package com.hhtholy.entity.bak;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:50
 */
@Entity
@Table(name="cp")
public class Cp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    //主键

    @ManyToOne
    @JoinColumn(name="cid")
    private Categorybak categorybak;

    @ManyToOne
    @JoinColumn(name="pid")
    private Propertybak propertybak;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categorybak getCategorybak() {
        return categorybak;
    }

    public void setCategorybak(Categorybak categorybak) {
        this.categorybak = categorybak;
    }

    public Propertybak getPropertybak() {
        return propertybak;
    }

    public void setPropertybak(Propertybak propertybak) {
        this.propertybak = propertybak;
    }
}
