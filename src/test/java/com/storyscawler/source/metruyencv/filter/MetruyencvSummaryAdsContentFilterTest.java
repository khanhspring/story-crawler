package com.storyscawler.source.metruyencv.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MetruyencvSummaryAdsContentFilterTest {

    @Test
    void test_filter_withNormalContent() {
        var mainContent = "Dao Trì Nữ Đế, Thần Quốc nữ hoàng, Tiên Môn Chi Tổ. . . Từng vị Vô Thượng Cự Đầu phá không mà ra!\n" +
                "\n" +
                "\"Phụng sư tôn chi mệnh, nhấc lên náo động giả, giết không tha!\"\n" +
                "\n" +
                "Luyện Thể, Chân Nguyên, Địa Huyền, Thiên Huyền, Ích Hải, Cửu Kiếp, Vương Cảnh...\n" +
                "\n" +
                "tag: sảng văn, cùng tác giả bộ Ta! Thu Đồ Đệ Liền Mạnh Mẽ!, có kinh nghiệm viết thể loại này";

        var content = mainContent +
                "\n" +
                "CẦU HOA TƯƠI, CẦU BUFF KẸO (NẠP BÊN VTRUYEN), CẦU THẢ TYM, LIKE CUỐI CHƯƠNG （づ￣3￣）づ╭❤～\n" +
                "XEM NHIỀU TRUYỆN HAY KHÁC CVT Yurisa LÀM Ở https://metruyencv.com/ho-so/1000010\n" +
                "(hoặc bấm vào tên Yurisa ở web, hay \"cùng đăng bởi Yurisa\" ở app MTC - Mê Truyện Chữ)\n" +
                "\n";

        var result = new MetruyencvSummaryAdsContentFilter().filter(content);
        Assertions.assertEquals(mainContent, result);
    }

    @Test
    void test_filter_withEmptyContent() {
        var result = new MetruyencvSummaryAdsContentFilter().filter(null);
        Assertions.assertNull(result);
    }

    @Test
    void test_filter_withContentHasNoAds() {
        var mainContent = "Dao Trì Nữ Đế, Thần Quốc nữ hoàng, Tiên Môn Chi Tổ. . . Từng vị Vô Thượng Cự Đầu phá không mà ra!\n" +
                "\n" +
                "\"Phụng sư tôn chi mệnh, nhấc lên náo động giả, giết không tha!\"\n" +
                "\n" +
                "Luyện Thể, Chân Nguyên, Địa Huyền, Thiên Huyền, Ích Hải, Cửu Kiếp, Vương Cảnh...\n" +
                "\n" +
                "tag: sảng văn, cùng tác giả bộ Ta! Thu Đồ Đệ Liền Mạnh Mẽ!, có kinh nghiệm viết thể loại này";

        var result = new MetruyencvSummaryAdsContentFilter().filter(mainContent);
        Assertions.assertEquals(mainContent, result);
    }

    @Test
    void test_filter_withLongContent() {
        var mainContent = "« mạng tiểu thuyết độc nhất vô nhị ký hợp đồng tiểu thuyết: Hồng Hoang Thạch Cơ, nhân gian thanh tỉnh »\n" +
                "\n" +
                "Thạch Cơ dưới cơ duyên xảo hợp đạt được một vị xuyên việt giả ký ức, lúc đầu, nàng cho là Vực Ngoại Thiên Ma quấy phá, thẳng đến Hồng Quân thành thánh, lúc này mới tin tưởng.\n" +
                "\n" +
                "Nghĩ tới tương lai chính mình ngu xuẩn lại xui xẻo bi thảm nhân sinh, nàng phòng ngừa rắc rối có thể xuất hiện, từ vừa mới bắt đầu liền phòng ngừa chu đáo, vững vàng phát dục.\n" +
                "\n" +
                "Tiệt Giáo ?\n" +
                "\n" +
                "Cái kia chính là một cái hố to, tuyệt không gia nhập vào!\n" +
                "\n" +
                "Xiển Giáo ?\n" +
                "\n" +
                "Vậy cũng là một ít tâm hắc tay ngoan gia hỏa!\n" +
                "\n" +
                "Hồng Hoang quá nguy hiểm, hơi bất lưu thần rất dễ dàng nhân quả triền thân, nghiệp lực sâu nặng.\n" +
                "\n" +
                "Thạch Cơ không cầu dũng cảm tiến tới, chỉ cầu ổn trung cầu tiến.\n" +
                "\n" +
                "Nàng lấy Khô Lâu sơn làm căn cơ, lấy xuyên việt giả ký ức làm căn cứ, chậm rãi mưu hoa, yên lặng lớn mạnh, chế công pháp, Tu Thần thông, luyện linh bảo, bồi thế lực, đi ra một cái độc thuộc về mình cầu đạo đường.\n" +
                "\n" +
                "Ức vạn năm phía sau, nàng đầu đỉnh Nhật Nguyệt, tay nâng Địa Phủ, nhìn xuống Hồng Hoang, cùng Thánh Nhân là bạn, cùng Đạo Tổ đánh cờ, khác loại thành đạo, soạn nhạc một đoạn Truyền Kỳ.\n" +
                "\n" +
                "tác giả nam nhé";

        var content = "« mạng tiểu thuyết độc nhất vô nhị ký hợp đồng tiểu thuyết: Hồng Hoang Thạch Cơ, nhân gian thanh tỉnh »\n" +
                "\n" +
                "Thạch Cơ dưới cơ duyên xảo hợp đạt được một vị xuyên việt giả ký ức, lúc đầu, nàng cho là Vực Ngoại Thiên Ma quấy phá, thẳng đến Hồng Quân thành thánh, lúc này mới tin tưởng.\n" +
                "\n" +
                "Nghĩ tới tương lai chính mình ngu xuẩn lại xui xẻo bi thảm nhân sinh, nàng phòng ngừa rắc rối có thể xuất hiện, từ vừa mới bắt đầu liền phòng ngừa chu đáo, vững vàng phát dục.\n" +
                "\n" +
                "Tiệt Giáo ?\n" +
                "\n" +
                "Cái kia chính là một cái hố to, tuyệt không gia nhập vào!\n" +
                "\n" +
                "Xiển Giáo ?\n" +
                "\n" +
                "Vậy cũng là một ít tâm hắc tay ngoan gia hỏa!\n" +
                "\n" +
                "Hồng Hoang quá nguy hiểm, hơi bất lưu thần rất dễ dàng nhân quả triền thân, nghiệp lực sâu nặng.\n" +
                "\n" +
                "Thạch Cơ không cầu dũng cảm tiến tới, chỉ cầu ổn trung cầu tiến.\n" +
                "\n" +
                "Nàng lấy Khô Lâu sơn làm căn cơ, lấy xuyên việt giả ký ức làm căn cứ, chậm rãi mưu hoa, yên lặng lớn mạnh, chế công pháp, Tu Thần thông, luyện linh bảo, bồi thế lực, đi ra một cái độc thuộc về mình cầu đạo đường.\n" +
                "\n" +
                "Ức vạn năm phía sau, nàng đầu đỉnh Nhật Nguyệt, tay nâng Địa Phủ, nhìn xuống Hồng Hoang, cùng Thánh Nhân là bạn, cùng Đạo Tổ đánh cờ, khác loại thành đạo, soạn nhạc một đoạn Truyền Kỳ.\n" +
                "\n" +
                "tác giả nam nhé\n" +
                "\n" +
                "CẦU HOA TƯƠI, CẦU BUFF KẸO (NẠP BÊN VTRUYEN), CẦU THẢ TYM, LIKE CUỐI CHƯƠNG （づ￣3￣）づ╭❤～\n" +
                "XEM NHIỀU TRUYỆN HAY KHÁC CVT Yurisa LÀM Ở https://metruyencv.com/ho-so/1000010\n" +
                "(hoặc bấm vào tên Yurisa ở web, hay \"cùng đăng bởi Yurisa\" ở app MTC - Mê Truyện Chữ)";
        var result = new MetruyencvSummaryAdsContentFilter().filter(content);
        Assertions.assertEquals(mainContent, result);
    }
}