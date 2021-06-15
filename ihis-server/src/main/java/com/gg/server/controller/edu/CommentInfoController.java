package com.gg.server.controller.edu;


import com.gg.server.entity.edu.CommentInfo;
import com.gg.server.entity.edu.UserOperate;
import com.gg.server.mapper.edu.CommentInfoMapper;
import com.gg.server.mapper.edu.UserOperateMapper;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.RespPageBean;
import com.gg.server.service.edu.CommentInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 作品评论信息表 前端控制器
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/m/comment")
public class CommentInfoController {

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private UserOperateMapper userOperateMapper;

    @ApiOperation(value = "查询所有评论")
    @GetMapping("/getComments")
    public RespPageBean getComments(@RequestParam String type,
                                    @RequestParam String source,
                                    @RequestParam(required = false,defaultValue = "1") String offset,
                                    @RequestParam String limit){
        return commentInfoService.getComments(type, source, offset, limit);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/addComments")
    public RespBean addComment(@RequestBody CommentInfo commentInfo){
        return commentInfoService.addComment(commentInfo);
    }

    @ApiOperation(value = "收藏资讯")
    @GetMapping("/updateCollectArticle")
    public RespBean updateCollectArticle(@RequestParam Integer oid, @RequestParam Integer collect){
        UserOperate userOperate = userOperateMapper.selectById(oid);
        userOperate.setCollect(collect);
        userOperateMapper.updateById(userOperate);
        return  RespBean.success("修改成功");
    }

    @ApiOperation(value = "点赞资讯")
    @GetMapping("/updateLikeArticle")
    public RespBean updateLikeArticle(@RequestParam Integer oid, @RequestParam Integer isLike){
        UserOperate userOperate = userOperateMapper.selectById(oid);
        userOperate.setIsLike(isLike);
        userOperateMapper.updateById(userOperate);
        return  RespBean.success("修改成功");
    }



}

