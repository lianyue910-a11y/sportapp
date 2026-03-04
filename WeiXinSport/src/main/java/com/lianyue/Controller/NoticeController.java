package com.lianyue.Controller;

import com.lianyue.Service.NoticeService;
import com.lianyue.pojo.Notice;
import com.lianyue.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    // 公共：列表
    @GetMapping("/list")
    public Result list() {
        return Result.success(noticeService.getList());
    }

    // 管理员：发布
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        if (noticeService.addNotice(notice) > 0){
            return Result.success("发布成功");
        }else return Result.error("发布失败");
    }

    // 管理员：删除
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Integer> map) {
        if (noticeService.deleteNotice(map.get("id")) > 0){
            return Result.success("删除成功");
        }else return Result.error("删除失败");
    }
}
