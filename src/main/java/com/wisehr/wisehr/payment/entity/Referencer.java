package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "referencer")
public class Referencer {
    @EmbeddedId
    private ReferencerPK referencerPK;

    public Referencer(ReferencerPK referencerPK) {
        this.referencerPK = referencerPK;
    }

    public ReferencerPK getReferencerPK() {
        return referencerPK;
    }

    public void setReferencerPK(ReferencerPK referencerPK) {
        this.referencerPK = referencerPK;
    }

    @Override
    public String toString() {
        return "Referencer{" +
                "referencerPK=" + referencerPK +
                '}';
    }

    public Referencer() {
    }
}
