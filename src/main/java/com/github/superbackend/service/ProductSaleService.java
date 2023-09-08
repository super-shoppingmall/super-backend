package com.github.superbackend.service;

import com.github.superbackend.dto.ProductSaleReqDto;
import com.github.superbackend.dto.ProductSaleResDto;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductImage;
import com.github.superbackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductSaleService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;
    private final String POST_IMAGE_DIR = "product";

    @Transactional
    public ProductSaleResDto registerProductSale(String username, ProductSaleReqDto reqDto) {
        // 로그인 한 사람 = 판매자
        //Member seller = memberRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("No MemberId"));
        Member seller = memberRepository.findByEmail(username);
        System.out.println("셀러" + seller);

        // 이미지가 있다면 S3에 업로드 후 URL 가져오기
        //List<String> imageUrls = s3Uploader.uploadFiles(reqDto.getProductImageFiles());
        List<String> imageUrls = s3Uploader.upload(reqDto.getProductImageFiles(), POST_IMAGE_DIR);
        for(String img : imageUrls){
            System.out.println("순서대로이미지주소"+img);
        }
        System.out.println("uploadFiles 후=======");
        // 상품 정보 저장
        Product product = new Product();
        product.setProductName(reqDto.getProductName());
        product.setCategoryName(reqDto.getCategoryName());
        product.setProductPrice(reqDto.getProductPrice());
        product.setProductQuantity(reqDto.getProductQuantity());
        product.setProductDetail(reqDto.getProductDetail());
        product.setClosingAt(reqDto.getClosingAt());


        for (String imageUrl : imageUrls) {  // 이미지 URL 설정
            product.getImages().add(new ProductImage(imageUrl));
            System.out.println("imageUrl 하니씩 출력 "+imageUrl);
        }

        //product.setMember(seller);  // 판매자 설정 <-필요한것인가..?

        // DB에 저장
        Product savedProduct = productRepository.save(product);

        System.out.println("savedProduct 저장 후 " + savedProduct);

        //return savedProduct; // 저장된 Product 객체 반환
        return convertToDto(savedProduct);  // DTO 변환 후 반환환
    }

    // Entity -> DTO 변환 메소드
    public ProductSaleResDto convertToDto(Product savedproduct) {
        ProductSaleResDto resDto = new ProductSaleResDto();
        // resDto.setMemberId(savedproduct.getMember().getMemberId());
        resDto.setProductId(savedproduct.getProductId());
        resDto.setProductName(savedproduct.getProductName());
        resDto.setCategoryName(savedproduct.getCategoryName());
        resDto.setProductPrice(savedproduct.getProductPrice());
        resDto.setProductQuantity(savedproduct.getProductQuantity());
        resDto.setProductDetail(savedproduct.getProductDetail());
        resDto.setClosingAt(savedproduct.getClosingAt());
        resDto.setProductImageUrls(savedproduct.getImages().stream()
                .map(ProductImage::getProductImageUrl)
                .collect(Collectors.toList()));
        return resDto;
    }
}
