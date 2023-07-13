package uz.najottalim.javan6.repository.extension;

public interface ProfileRepositoryExtension {
    void addFollower(Long userId, Long followerId);

    boolean isCurrentUserFollow(Long userId, Long followerId);

    void deleteFollower(Long id, Long id1);
}
