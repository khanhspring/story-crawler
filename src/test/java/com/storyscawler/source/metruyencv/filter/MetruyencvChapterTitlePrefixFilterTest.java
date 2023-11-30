package com.storyscawler.source.metruyencv.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MetruyencvChapterTitlePrefixFilterTest {

    @Test
    void test_filter_withColon() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter("Chương 05: Không gian phong ấn" );
        Assertions.assertEquals("Không gian phong ấn", result);
    }

    @Test
    void test_filter_withDot() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter("Chương 05. Không gian phong ấn" );
        Assertions.assertEquals("Không gian phong ấn", result);
    }

    @Test
    void test_filter_withoutDotOrColon() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter("Chương 05 Không gian phong ấn" );
        Assertions.assertEquals("Không gian phong ấn", result);
    }

    @Test
    void test_filter_withoutSpaces() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter("Chương 05 : Không gian phong ấn" );
        Assertions.assertEquals("Không gian phong ấn", result);
    }

    @Test
    void test_filter_withoutMultipleSpaces() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter("Chương 05      :     Không gian phong ấn" );
        Assertions.assertEquals("Không gian phong ấn", result);
    }

    @Test
    void test_filter_withEmptyContent() {
        var result = new MetruyencvChapterTitlePrefixFilter().filter(null);
        Assertions.assertNull(result);
    }
}