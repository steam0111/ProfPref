package com.example.stanislavk.profpref.di.services.firebase.models.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LasVegas on 11.07.2017.
 */

public class ModelTest {
    private List<ModelCategories> categories = new ArrayList<>();

    public ModelTest() {

    }

  public List<ModelCategories> getCategories() {
    return categories;
  }

  public void setCategories(List<ModelCategories> categories) {
    this.categories = categories;
  }
}
