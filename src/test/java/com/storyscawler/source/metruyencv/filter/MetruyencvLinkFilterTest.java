package com.storyscawler.source.metruyencv.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MetruyencvLinkFilterTest {

    @Test
    void test_filter_whenContentHasLinks() {
        var content = "Dao Trì Nữ Đế, Thần Quốc nữ hoàng, Tiên Môn Chi Tổ. . . Từng vị Vô Thượng Cự Đầu phá không mà ra!\n" +
                "\n" +
                "\"Phụng sư tôn chi mệnh, nhấc lên náo động giả, giết không tha!\"\n" +
                "\n" +
                "Luyện Thể, https://metruyencv.com/ho-so/1000010?a=b&b=c&d=2123123 Chân Nguyên, Địa Huyền, Thiên Huyền, Ích Hải, Cửu Kiếp, Vương Cảnh...\n" +
                "\n" +
                "tag: sảng văn, https://metruyencv.com/ho-so/1000010?a=b&b=c&d=2123123 cùng tác giả bộ Ta! Thu Đồ Đệ Liền Mạnh Mẽ!, có kinh nghiệm viết thể loại này";

        var cleanContent = "Dao Trì Nữ Đế, Thần Quốc nữ hoàng, Tiên Môn Chi Tổ. . . Từng vị Vô Thượng Cự Đầu phá không mà ra!\n" +
                "\n" +
                "\"Phụng sư tôn chi mệnh, nhấc lên náo động giả, giết không tha!\"\n" +
                "\n" +
                "Luyện Thể, Chân Nguyên, Địa Huyền, Thiên Huyền, Ích Hải, Cửu Kiếp, Vương Cảnh...\n" +
                "\n" +
                "tag: sảng văn, cùng tác giả bộ Ta! Thu Đồ Đệ Liền Mạnh Mẽ!, có kinh nghiệm viết thể loại này";
        var result = new MetruyencvLinkContentFilter().filter(content);
        assertEquals(cleanContent, result);
    }


    @Test
    void test_filter_whenContentHasNoLink() {
        var content = "Dao Trì Nữ Đế, Thần Quốc nữ hoàng, Tiên Môn Chi Tổ. . . Từng vị Vô Thượng Cự Đầu phá không mà ra!\n" +
                "\n" +
                "\"Phụng sư tôn chi mệnh, nhấc lên náo động giả, giết không tha!\"\n" +
                "\n" +
                "Luyện Thể, Chân Nguyên, Địa Huyền, Thiên Huyền, Ích Hải, Cửu Kiếp, Vương Cảnh...\n" +
                "\n" +
                "tag: sảng văn, cùng tác giả bộ Ta! Thu Đồ Đệ Liền Mạnh Mẽ!, có kinh nghiệm viết thể loại này";

        var result = new MetruyencvLinkContentFilter().filter(content);
        assertEquals(content, result);
    }

    @Test
    void test_filter_whenContentIsEmpty() {
        var result = new MetruyencvLinkContentFilter().filter(null);
        assertNull(result);
    }
}