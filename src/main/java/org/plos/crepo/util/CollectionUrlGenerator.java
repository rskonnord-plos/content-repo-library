/*
 * Copyright 2017 Public Library of Science
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.plos.crepo.util;

import java.util.Map;

import static org.plos.crepo.util.BaseUrlGenerator.*;

/**
 * Generates the content repo urls for collections services.
 */
public class CollectionUrlGenerator {

  private static final String CREATE_COLLECTION_URL = "${repoServer}/collections";
  private static final String COLLECTION_URL = "${repoServer}/collections/${bucketName}?key=${collectionKey}";
  private static final String COLLECTION_USING_VERSION_NUM_URL = COLLECTION_URL + "&version=${versionNumber}";
  private static final String COLLECTION_USING_VERSION_CKS_URL = COLLECTION_URL + "&uuid=${uuid}";
  private static final String COLLECTION_USING_TAG_URL = COLLECTION_URL +  "&tag=${tag}";
  private static final String COLLECTION_VERSIONS_URL = "${repoServer}/collections/versions/${bucketName}?key=${collectionKey}";
  private static final String COLLECTIONS_URL = "${repoServer}/collections?bucketName=${bucketName}&offset=${offset}&limit=${limit}&includeDeleted=${includeDeleted}";
  private static final String COLLECTIONS_USING_TAG_URL = COLLECTIONS_URL + "&tag=${tag}";

  public static String getCollectionVersionNumUrl(String repoServer, String bucketName, String collKey, int versionNumber) {
    return replaceUrl(COLLECTION_USING_VERSION_NUM_URL, getCollectionMapWithVersionNumber(repoServer, bucketName, collKey, versionNumber));
  }

  public static String getCollectionTagUrl(String repoServer, String bucketName, String collKey, String tag) {
    return replaceUrl(COLLECTION_USING_TAG_URL, getCollectionMapWithTag(repoServer, bucketName, collKey, tag));
  }

  public static String getCollectionUuidUrl(String repoServer, String bucketName, String collKey, String uuid) {
    return replaceUrl(COLLECTION_USING_VERSION_CKS_URL, getCollectionMapWithUuid(repoServer, bucketName, collKey, uuid));
  }

  public static String getCollectionVersionsUrl(String repoServer, String bucketName, String collKey) {
    return replaceUrl(COLLECTION_VERSIONS_URL, getCollectionBasicMap(repoServer, bucketName, collKey));
  }


  public static String getCollectionsUsingTagUrl(String repoServer, String bucketName, int offset, int limit, boolean includeDeleted, String tag) {
    return replaceUrl(COLLECTIONS_USING_TAG_URL, getContentInBucketMap(repoServer, bucketName, offset, limit, includeDeleted, tag));
  }

  public static String getLatestCollectionUrl(String repoServer, String bucketName, String key) {
    return replaceUrl(COLLECTION_URL, getCollectionBasicMap(repoServer, bucketName, key));
  }

  public static String getGetCollectionsUrl(String repoServer, String bucketName, int offset, int limit, boolean includeDelete) {
    return replaceUrl(COLLECTIONS_URL, getContentInBucketMap(repoServer, bucketName, offset, limit, includeDelete));
  }

  public static String getCreateCollUrl(String repoServer) {
    return replaceUrl(CREATE_COLLECTION_URL, getUrlBasicMap(repoServer));
  }

  private static Map<String, String> getCollectionBasicMap(String repoServer, String bucketName, String collKey) {
    Map<String, String> values = getBucketBasicMap(repoServer, bucketName);
    values.put("collectionKey", collKey);
    return values;
  }

  private static Map<String, String> getCollectionMapWithUuid(String repoServer, String bucketName, String collKey, String uuid) {
    Map<String, String> values = getCollectionBasicMap(repoServer, bucketName, collKey);
    values.put("uuid", uuid);
    return values;
  }

  private static Map<String, String> getCollectionMapWithVersionNumber(String repoServer, String bucketName, String collKey, int versionNumber) {
    Map<String, String> values = getCollectionBasicMap(repoServer, bucketName, collKey);
    values.put("versionNumber", String.valueOf(versionNumber));
    return values;
  }

  private static Map<String, String> getCollectionMapWithTag(String repoServer, String bucketName, String collKey, String tag) {
    Map<String, String> values = getCollectionBasicMap(repoServer, bucketName, collKey);
    values.put("tag", String.valueOf(tag));
    return values;
  }

}
