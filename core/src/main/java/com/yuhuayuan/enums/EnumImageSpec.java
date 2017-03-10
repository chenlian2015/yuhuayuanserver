package com.yuhuayuan.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cl on 2017/3/10.
 */

public enum EnumImageSpec {
    ORIG("orig", ""),
    SPEC_750_280("750x280", "@!750x280", 750, 280, 50),
    SPEC_288_216("288x216", "@!288x216", 288, 216, 50),
    SPEC_710_240("710x240", "@!710x240", 710, 240, 50),
    SPEC_236_177("236x177", "@!236x177", 236, 177, 50),
    SPEC_160_160("160x160", "@!160x160", 160, 160, 50),
    SPEC_320_140("320x140", "@!320x140", 320, 140, 50),
    SPEC_60_60("60x60", "@!60x60", 60, 60, 50),
    SPEC_344_258("344x258", "@!344x258", 344, 258, 50),
    SPEC_100_75("100x75", "@!100x75", 100, 75, 50),
    SPEC_750_562("750x562", "@!750x562", 750, 562, 50),
    SPEC_100_100("100x100", "@!100x100", 100, 100, 50),
    SPEC_40_30("40x30", "@!40x30", 40, 30, 50),
    SPEC_100_43("100x43", "@!100x43", 100, 43, 50),
    SPEC_100_37("100x37", "@!100x37", 100, 37, 50),
    SPEC_180_135("180x135", "@!180x135", 180, 135, 50),
    SPEC_750("750", "@!750", 750, 0, 50);

    private String code;
    private String name;
    private int width;
    private int height;
    private int quality;

    private EnumImageSpec(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private EnumImageSpec(String code, String name, int width, int height, int quality) {
        this.code = code;
        this.name = name;
        this.width = width;
        this.height = height;
        this.quality = quality;
    }

    public static List<EnumImageSpec> getThumbSpec() {
        ArrayList list = new ArrayList();
        EnumImageSpec[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EnumImageSpec spec = var1[var3];
            if(spec.name().startsWith("SPEC")) {
                list.add(spec);
            }
        }

        return list;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
