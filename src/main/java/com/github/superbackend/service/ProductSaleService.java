package com.github.superbackend.service;

import com.github.superbackend.dto.ProductSaleReqDto;
import com.github.superbackend.dto.ProductSaleResDto;
import com.github.superbackend.repository.member.Member;
import com.github.superbackend.repository.member.MemberRepository;
<<<<<<< HEAD
import com.github.superbackend.repository.product.*;
=======
import com.github.superbackend.repository.product.Product;
import com.github.superbackend.repository.product.ProductImage;
import com.github.superbackend.repository.product.ProductRepository;
import com.github.superbackend.repository.product.ProductStatus;
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.Collections;
=======
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
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
<<<<<<< HEAD
=======
        //Member seller = memberRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("No MemberId"));
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
        Member seller = memberRepository.findByEmail(username);
        //System.out.println("셀러" + seller);

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
        product.setProductStatus(ProductStatus.ACTIVE);
        product.setClosingAt(reqDto.getClosingAt());
        product.setMember(seller);


        for (String imageUrl : imageUrls) {  // 이미지 URL 설정
            product.getImages().add(new ProductImage(imageUrl, product));
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
<<<<<<< HEAD
        resDto.setMemberId(savedproduct.getMember().getMemberId());
=======
        // resDto.setMemberId(savedproduct.getMember().getMemberId());
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
        resDto.setProductId(savedproduct.getProductId());
        resDto.setProductName(savedproduct.getProductName());
        resDto.setCategoryName(savedproduct.getCategoryName());
        resDto.setProductPrice(savedproduct.getProductPrice());
        resDto.setProductQuantity(savedproduct.getProductQuantity());
        resDto.setProductDetail(savedproduct.getProductDetail());
        resDto.setClosingAt(savedproduct.getClosingAt());
<<<<<<< HEAD
        resDto.setProductStatus(savedproduct.getProductStatus().name());
=======
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
        resDto.setProductImageUrls(savedproduct.getImages().stream()
                .map(ProductImage::getProductImageUrl)
                .collect(Collectors.toList()));
        return resDto;
    }

    @Transactional(readOnly = true)
<<<<<<< HEAD
    public List<ProductSaleResDto> getProductsOnSale(String username, boolean isActive) {
        Member seller = memberRepository.findByEmail(username);
        System.out.println(seller.toString());

        List<Product> products;

        if (seller != null) {
            if(isActive) {
                // 물품 상태가 ProductStatus.ACTIVE 인 물품만 가져오도록
                products = productRepository.findByMemberAndProductStatus(seller, ProductStatus.ACTIVE);
                //List<Product> products = productRepository.findByMember(seller);

            }
            else { //sale finised and closed
                products = productRepository.findByMemberAndProductStatusIsNotActive();
            }

            if (!products.isEmpty()) {
                return products.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            }


        }

        // 물품이 없거나 판매자를 찾지 못한 경우 빈 리스트 반환
        return Collections.emptyList(); // 근데 아까 404 에러 요걸로 나나
    }


    @Transactional
    public ProductSaleResDto updateProductQuantity(Long productId, int newQuantity, String username) {
        Member seller = memberRepository.findByEmail(username);
        Product product = productRepository.findByProductIdAndMember(productId, seller);

        if (product != null) {
            // 재고 수정 로직 구현
            if (newQuantity >= 0) {
                product.setProductQuantity(newQuantity);
                Product updatedProduct = productRepository.save(product);
                return convertToDto(updatedProduct);
            }
        }

        return null; // 재고 수정에 실패한 경우 null 반환
=======
    public List<ProductSaleResDto> getProductsOnSale(String username) {
        Member seller = memberRepository.findByEmail(username);

        List<Product> products = productRepository.findByMemberAndClosingAtAfter(seller, LocalDate.now());

        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
    }

    @Transactional
    public void updateProductStatus() {
        productRepository.updateStatusForPastProducts(ProductStatus.CLOSED, LocalDate.now());
    }
}
