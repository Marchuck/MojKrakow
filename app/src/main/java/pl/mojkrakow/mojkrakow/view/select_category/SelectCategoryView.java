package pl.mojkrakow.mojkrakow.view.select_category;

import java.util.List;

import pl.mojkrakow.mojkrakow.view.IssueCategory;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public interface SelectCategoryView {
    void showCategories(List<IssueCategory> categoryList);
}
