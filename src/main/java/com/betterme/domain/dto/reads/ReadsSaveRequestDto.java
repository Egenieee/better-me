package com.betterme.domain.dto.reads;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Reads;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class ReadsSaveRequestDto {

    private Long betterMeId;

    private String name;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private int firstPage;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private int lastPage;

    @Length(max = 500)
    private String summary;

    public Reads toEntity(BetterMe betterMe) {
        return Reads.builder()
                .betterMe(betterMe)
                .name(name)
                .firstPage(firstPage)
                .lastPage(lastPage)
                .summary(summary)
                .build();
    }
}
