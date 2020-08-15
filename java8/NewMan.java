package com.atguigu.java8;

import java.util.Optional;

public class NewMan {
    //不能赋值null，可以赋值Optional.empty
    private Optional<Godness> godness=Optional.empty();//男人有可能没有女神，对这种有可能为null的值可以用Optional类包装

    public NewMan(Optional<Godness> godness) {
        this.godness = godness;
    }

    public NewMan() {
    }

    @Override
    public String toString() {
        return "NewMan{" +
                "godness=" + godness +
                '}';
    }

    public Optional<Godness> getGodness() {
        return godness;
    }

    public void setGodness(Optional<Godness> godness) {
        this.godness = godness;
    }
}
