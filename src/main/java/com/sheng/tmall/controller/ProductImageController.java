package com.sheng.tmall.controller;

import com.sheng.tmall.pojo.Product;
import com.sheng.tmall.pojo.ProductImage;
import com.sheng.tmall.service.ProductImageService;
import com.sheng.tmall.service.ProductService;
import com.sheng.tmall.util.ImageUtil;
import com.sheng.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product p = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(pi);
        String fileName = pi.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (ProductImageService.type_detail.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }

        File file = new File(imageFolder, fileName);
        file.getParentFile().mkdirs();

        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image = ImageUtil.change2jpg(file);
            ImageIO.write(image, "jpg", file);
            if (ProductImageService.type_single.equals(pi.getType())) {
                File file_small = new File(imageFolder_small, fileName);
                File file_middle = new File(imageFolder_middle, fileName);
                file_small.getParentFile().mkdirs();
                file_middle.getParentFile().mkdirs();

                ImageUtil.resizeImage(file,56,56,file_small);
                ImageUtil.resizeImage(file,217,190,file_middle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session) {
        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (ProductImageService.type_single.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");

            File file_small = new File(imageFolder_small, fileName);
            File file_middle = new File(imageFolder_middle, fileName);
            File file = new File(imageFolder, fileName);

            file.delete();
            file_small.delete();
            file_middle.delete();

        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File file = new File(imageFolder, fileName);
            file.delete();
        }
        productImageService.delete(id);

        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }
}
