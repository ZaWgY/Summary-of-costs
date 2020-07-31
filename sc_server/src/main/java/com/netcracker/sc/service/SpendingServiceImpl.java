package com.netcracker.sc.service;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.Spending;
import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.SpendingDTO;
import com.netcracker.sc.dto.SpendingRepresentDTO;
import com.netcracker.sc.enums.GroupRole;
import com.netcracker.sc.mapper.SpendingMapper;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.SpendingRepository;
import com.netcracker.sc.repository.UserRepository;
import com.netcracker.sc.repository.UserToGroupRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Service
public class SpendingServiceImpl implements SpendingService {

    private SpendingRepository spendingRepository;
    private SpendingMapper spendingMapper;
    private GroupRepository groupRepository;
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;
    private UserToGroupRepository userToGroupRepository;

    public SpendingServiceImpl(UserToGroupRepository userToGroupRepository, UserRepository userRepository, JavaMailSender javaMailSender, SpendingRepository spendingRepository,SpendingMapper spendingMapper, GroupRepository groupRepository) {
        this.spendingRepository = spendingRepository;
        this.spendingMapper = spendingMapper;
        this.groupRepository = groupRepository;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.userToGroupRepository = userToGroupRepository;
    }

    @Override
    public List<SpendingRepresentDTO> findByGroupId(Long groupId) {
        return spendingMapper.toRepresentDTOS(spendingRepository.findByGroupId(groupId));
    }

    @Override
    public Boolean addSpending(SpendingDTO spendingDTO) {
        try {
            Group group = this.groupRepository.findByGroupId(spendingDTO.getGroupId());
            BigDecimal currentAmount = spendingDTO.getAmount().add(group.getCurrentAmount());
            group.setCurrentAmount(currentAmount);
            BigDecimal maxAmount = group.getMaxAmount();
            this.groupRepository.save(group);
            spendingRepository.save(spendingMapper.toEntity(spendingDTO));
            if (maxAmount.compareTo(currentAmount) <= 0) {
                User owner = this.userToGroupRepository.findAllByGroupAndGroupRole(group, GroupRole.OWNER).get(0).getUser();
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(owner.getEmail());
                simpleMailMessage.setFrom("hogwartssocialnetwork@gmail.com");
                simpleMailMessage.setSubject("Превышен лимит на Summary of costs");
                simpleMailMessage.setText("Здравствуйте "+ owner.getFirstName()+","+'\n'+"На вашем проекте \"" + group.getName() + "\" был привышен лимит затрат. Сделайте с этим что-ниубдь.");
                javaMailSender.send(simpleMailMessage);
                /*new Thread(()->{

                }).run();*/
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<SpendingRepresentDTO> findByGroupIdAndUserId(Long groupId, Long userId) {
        return spendingMapper.toRepresentDTOS(spendingRepository.findByGroupIdAndUserId(groupId, userId));
    }

    @Transactional
    @Override
    public Boolean deleteSpending(Long spendingId) {
        try {
            Spending spending = spendingRepository.findBySpendingId(spendingId);
            Group group = spending.getGroup();
            BigDecimal currentAmount = group.getCurrentAmount();
            group.setCurrentAmount(currentAmount.subtract(spending.getAmount()));
            groupRepository.save(group);
            spendingRepository.deleteSpendingBySpendingId(spendingId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
