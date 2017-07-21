package com.example.beavi5.shvabrashvabr.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by beavi5 on 21.07.2017.
 */
public class RSSItemTest {
    String desc;
    String htmlTrue;


    @Before
    public void setUp() throws Exception {


        desc = "  <a class=\"header-navlink name tooltipped tooltipped-sw js-menu-target\" href=\"/beavi5\"\n" +
                "       aria-label=\"View profile and more\"\n" +
                "       aria-expanded=\"false\"\n" +
                "       aria-haspopup=\"true\"\n" +
                "       data-ga-click=\"Header, show menu, icon:avatar\">\n" +
                "      <img alt=\"@beavi5\" class=\"avatar\" src=\"https://avatars3.githubusercontent.com/u/11004535?v=4&amp;s=40\">\n" +
                "      <span class=\"dropdown-caret\"></span>\n" +
                "    </a>\n";


        htmlTrue = "  <a class=\"header-navlink name tooltipped tooltipped-sw js-menu-target\" href=\"/beavi5\"\n" +
                "       aria-label=\"View profile and more\"\n" +
                "       aria-expanded=\"false\"\n" +
                "       aria-haspopup=\"true\"\n" +
                "       data-ga-click=\"Header, show menu, icon:avatar\">\n" +
                "      <img width=\"100%\"  alt=\"@beavi5\" class=\"avatar\" src=\"https://avatars3.githubusercontent.com/u/11004535?v=4&amp;s=40\">\n" +
                "      <span class=\"dropdown-caret\"></span>\n" +
                "    </a>\n";


    }


    /** тест проверяет правильно ли задается ширина для картинок в хтмл коде*/
    @Test
public void setWidthToAllImgs() {
        desc = desc.replace("<img","<img width=\"100%\" ");

        assertEquals(htmlTrue,desc);

    }



    @After
    public void tearDown() throws Exception {

    }

}