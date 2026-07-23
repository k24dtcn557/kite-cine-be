package vn.id.hph.kitecine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.id.hph.kitecine.entity.FileMgmt;

@Repository
public interface FileMgmtRepository extends JpaRepository<FileMgmt, String> {

    Optional<FileMgmt> findById(String id);

    Optional<FileMgmt> findByName(String path);

    Optional<FileMgmt> findByNameAndParentId(String name, String parentId);

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'root'")
    Optional<FileMgmt> findRoot();

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'public'")
    Optional<FileMgmt> findPublic();

    @Query("SELECT f FROM FileMgmt f WHERE f.name = :folderName")
    Optional<FileMgmt> findFolder(String folderName);

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = :folderName")
    Optional<FileMgmt> findLevel1Folder(String folderName);

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'admin'")
    Optional<FileMgmt> findAdmin();

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'passes'")
    Optional<FileMgmt> findPasses();

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'chat'")
    Optional<FileMgmt> findChat();

    @Query("SELECT f FROM FileMgmt f WHERE f.parentId IS NULL AND f.name = 'private-club'")
    Optional<FileMgmt> findPrivateClub();
}
