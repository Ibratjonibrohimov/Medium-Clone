package uz.najottalim.javan6.repository.extension.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.repository.extension.ProfileRepositoryExtension;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileRepositoryExtensionImpl implements ProfileRepositoryExtension {
    private final EntityManager entityManager;
    @Override
    @Transactional
    public void addFollower(Long userId, Long followerId) {
        String query = "insert into followers values(?,?)";
        entityManager.createNativeQuery(query)
                .setParameter(1,userId)
                .setParameter(2,followerId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public boolean isCurrentUserFollow(Long userId, Long followerId) {
        String query = "select * from followers where user_id = ? and follower_id = ?";
        List resultList = entityManager.createNativeQuery(query)
                .setParameter(1, userId)
                .setParameter(2, followerId)
                .getResultList();
        return !resultList.isEmpty();
    }

    @Override
    @Transactional
    public void deleteFollower(Long userId, Long followerId) {
        String query = "delete from followers where user_id = ? and follower_id = ?";
        entityManager.createNativeQuery(query)
                .setParameter(1,userId)
                .setParameter(2,followerId)
                .executeUpdate();
    }
}
