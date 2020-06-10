package com.example.dinokeylas.ewaste.model;

import com.google.firebase.firestore.Exclude;

public class TypeOfWasteModel {
    private String name = null;
    @Exclude String id;
    private boolean checked =false;

    public TypeOfWasteModel(String name, boolean checked) {
        super();
        this.name = name;
        this.checked = checked;
    }

    public  TypeOfWasteModel(){

    }

    @Exclude public String getId() {
        return id;
    }

    @Exclude public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
