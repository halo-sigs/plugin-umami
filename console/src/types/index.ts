export interface ConfigMap {
  /**
   *
   * @type {{ [key: string]: string; }}
   * @memberof ConfigMap
   */
  data?: {
    [key: string]: string;
  };
  /**
   *
   * @type {string}
   * @memberof ConfigMap
   */
  apiVersion: string;
  /**
   *
   * @type {string}
   * @memberof ConfigMap
   */
  kind: string;
  /**
   *
   * @type {Metadata}
   * @memberof ConfigMap
   */
  metadata: Metadata;
}

export interface Metadata {
  /**
   *
   * @type {Set<string>}
   * @memberof Metadata
   */
  finalizers?: Set<string> | null;
  /**
   *
   * @type {string}
   * @memberof Metadata
   */
  name: string;
  /**
   *
   * @type {{ [key: string]: string; }}
   * @memberof Metadata
   */
  labels?: {
    [key: string]: string;
  } | null;
  /**
   *
   * @type {{ [key: string]: string; }}
   * @memberof Metadata
   */
  annotations?: {
    [key: string]: string;
  } | null;
  /**
   *
   * @type {number}
   * @memberof Metadata
   */
  version?: number | null;
  /**
   *
   * @type {string}
   * @memberof Metadata
   */
  creationTimestamp?: string | null;
  /**
   *
   * @type {string}
   * @memberof Metadata
   */
  deletionTimestamp?: string | null;
}
