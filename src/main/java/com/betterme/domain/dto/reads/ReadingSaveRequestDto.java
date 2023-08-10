package com.betterme.domain.dto.reads;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Reading;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class ReadingSaveRequestDto {

    private Long betterMeId;

    private String name;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private String firstPage;

    @NumberFormat
    @Pattern(regexp = "^\\d+$", message = "숫자로 입력해주세요.")
    private String lastPage;

    @Length(max = 500)
    private String summary;

    public Reading toEntity(BetterMe betterMe) {
        return Reading.builder()
                .betterMe(betterMe)
                .name(name)
                .firstPage(Integer.parseInt(firstPage))
                .lastPage(Integer.parseInt(lastPage))
                .summary(summary)
                .build();
    }
}
