package com.yuhuayuan.tool;

/**
 * Created by cl on 2017/3/10.
 */

public class Pageable {
    protected int number;
    protected int size;
    protected int offset;

    public Pageable(int number, int size) {
        this.number = number;
        this.size = size;
        this.offset = (this.number - 1) * this.size;
    }

    public int getOffset() {
        return (this.number - 1) * this.size;
    }

    public int getNumber() {
        return this.number;
    }

    public int getSize() {
        return this.size;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Pageable)) {
            return false;
        } else {
            Pageable other = (Pageable)o;
            return !other.canEqual(this)?false:(this.getNumber() != other.getNumber()?false:(this.getSize() != other.getSize()?false:this.getOffset() == other.getOffset()));
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Pageable;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + this.getNumber();
        result1 = result1 * 59 + this.getSize();
        result1 = result1 * 59 + this.getOffset();
        return result1;
    }

    public String toString() {
        return "Pageable(number=" + this.getNumber() + ", size=" + this.getSize() + ", offset=" + this.getOffset() + ")";
    }

    public Pageable() {
    }
}

