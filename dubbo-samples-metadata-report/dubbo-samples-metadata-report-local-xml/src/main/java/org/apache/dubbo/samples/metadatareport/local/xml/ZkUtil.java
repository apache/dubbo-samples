package org.apache.dubbo.samples.metadatareport.local.xml;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.metadata.identifier.MetadataIdentifier;

/**
 * 2018/11/8
 */
public class ZkUtil {

    static String toRootDir(String root) {
        if (root.equals(Constants.PATH_SEPARATOR)) {
            return root;
        }
        return root + Constants.PATH_SEPARATOR;
    }

    public static String getNodePath(MetadataIdentifier metadataIdentifier) {
        return toRootDir("/dubbo") + metadataIdentifier.getFilePathKey() + Constants.PATH_SEPARATOR + "service.data";
    }
}
