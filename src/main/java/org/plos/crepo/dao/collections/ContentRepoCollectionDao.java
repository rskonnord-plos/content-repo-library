package org.plos.crepo.dao.collections;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.plos.crepo.model.RepoCollection;

public interface ContentRepoCollectionDao {

  CloseableHttpResponse createCollection(String bucketName, RepoCollection repoCollection);

  CloseableHttpResponse versionCollection(String bucketName, RepoCollection repoCollection);

  CloseableHttpResponse deleteCollectionUsingVersionCks(String bucketName, String key, String versionChecksum);

  CloseableHttpResponse deleteCollectionUsingVersionNumb(String bucketName, String key, int versionNumber);

  CloseableHttpResponse getCollectionUsingVersionCks(String bucketName, String key, String versionChecksum);

  CloseableHttpResponse getCollectionUsingVersionNumber(String bucketName, String key, int versionNumber);

  CloseableHttpResponse getCollectionUsingTag(String bucketName, String key, String tag);

  CloseableHttpResponse getCollectionVersions(String bucketName, String key);

  CloseableHttpResponse getCollections(String bucketName, int offset, int limit, boolean includeDeleted);

  CloseableHttpResponse getCollectionsUsingTag(String bucketName, int offset, int limit, boolean includeDeleted, String tag);


}
