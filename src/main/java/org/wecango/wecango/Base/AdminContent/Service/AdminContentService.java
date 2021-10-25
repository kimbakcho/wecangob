package org.wecango.wecango.Base.AdminContent.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wecango.wecango.Base.AdminContent.Domain.AdminContent;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentResDto;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentSaveReqDto;
import org.wecango.wecango.Base.AdminContent.Dto.AdminContentSimpleResDto;
import org.wecango.wecango.Base.AdminContent.Repository.AdminContentDataRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminContentService {

    final AdminContentDataRepository adminContentDataRepository;

    public AdminContentResDto save(AdminContentSaveReqDto reqDto) {

        AdminContent saveItem = AdminContent.builder()
                .title(reqDto.getTitle())
                .html(reqDto.getHtml())
                .markDown(reqDto.getMarkDown())
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .build();
        AdminContent save = adminContentDataRepository.save(saveItem);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(save,AdminContentResDto.class);
    }

    public AdminContentResDto update(AdminContentSaveReqDto reqDto) {
        AdminContent byId = adminContentDataRepository.getById(reqDto.getId());
        byId.setTitle(reqDto.getTitle());
        byId.setHtml(reqDto.getHtml());
        byId.setMarkDown(reqDto.getMarkDown());
        byId.setUpdateTime(LocalDateTime.now());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId,AdminContentResDto.class);
    }

    public AdminContentResDto getDoc(Integer id) {
        AdminContent byId = adminContentDataRepository.getById(id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(byId,AdminContentResDto.class);
    }

    public List<AdminContentSimpleResDto> getDocs() {
        List<AdminContentSimpleResDto> allSimpleByOrderByCreateTimeDesc =
                adminContentDataRepository.findAllSimpleByOrderByCreateTimeDesc();
        return allSimpleByOrderByCreateTimeDesc;
    }

    public void delDoc(Integer id) {
        adminContentDataRepository.deleteById(id);
    }
}
