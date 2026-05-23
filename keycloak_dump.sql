--
-- PostgreSQL database dump
--

-- Dumped from database version 11.4 (Debian 11.4-1.pgdg90+1)
-- Dumped by pg_dump version 11.4 (Debian 11.4-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64),
    details_json text
);


ALTER TABLE public.admin_event_entity OWNER TO keycloak;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO keycloak;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO keycloak;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO keycloak;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO keycloak;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO keycloak;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO keycloak;

--
-- Name: client; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO keycloak;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO keycloak;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO keycloak;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO keycloak;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO keycloak;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO keycloak;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO keycloak;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO keycloak;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO keycloak;

--
-- Name: component; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO keycloak;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.component_config OWNER TO keycloak;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO keycloak;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.credential OWNER TO keycloak;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO keycloak;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO keycloak;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO keycloak;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE public.event_entity OWNER TO keycloak;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024),
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.fed_user_attribute OWNER TO keycloak;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO keycloak;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO keycloak;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO keycloak;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO keycloak;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO keycloak;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO keycloak;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO keycloak;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO keycloak;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO keycloak;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO keycloak;

--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL,
    organization_id character varying(255),
    hide_on_login boolean DEFAULT false
);


ALTER TABLE public.identity_provider OWNER TO keycloak;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO keycloak;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO keycloak;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO keycloak;

--
-- Name: jgroups_ping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.jgroups_ping (
    address character varying(200) NOT NULL,
    name character varying(200),
    cluster_name character varying(200) NOT NULL,
    ip character varying(200) NOT NULL,
    coord boolean
);


ALTER TABLE public.jgroups_ping OWNER TO keycloak;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36),
    type integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.keycloak_group OWNER TO keycloak;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO keycloak;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO keycloak;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.offline_client_session OWNER TO keycloak;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL,
    broker_session_id character varying(1024),
    version integer DEFAULT 0
);


ALTER TABLE public.offline_user_session OWNER TO keycloak;

--
-- Name: org; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.org (
    id character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    realm_id character varying(255) NOT NULL,
    group_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(4000),
    alias character varying(255) NOT NULL,
    redirect_url character varying(2048)
);


ALTER TABLE public.org OWNER TO keycloak;

--
-- Name: org_domain; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.org_domain (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    verified boolean NOT NULL,
    org_id character varying(255) NOT NULL
);


ALTER TABLE public.org_domain OWNER TO keycloak;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO keycloak;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO keycloak;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO keycloak;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO keycloak;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO keycloak;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO keycloak;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO keycloak;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO keycloak;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO keycloak;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO keycloak;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO keycloak;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO keycloak;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO keycloak;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO keycloak;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO keycloak;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO keycloak;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO keycloak;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO keycloak;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO keycloak;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO keycloak;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO keycloak;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO keycloak;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO keycloak;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO keycloak;

--
-- Name: revoked_token; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.revoked_token (
    id character varying(255) NOT NULL,
    expire bigint NOT NULL
);


ALTER TABLE public.revoked_token OWNER TO keycloak;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO keycloak;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO keycloak;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO keycloak;

--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.user_attribute OWNER TO keycloak;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO keycloak;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO keycloak;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO keycloak;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO keycloak;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO keycloak;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO keycloak;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO keycloak;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL,
    membership_type character varying(255) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO keycloak;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO keycloak;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO keycloak;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: keycloak
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO keycloak;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type, details_json) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
98ee2dd5-9427-4f2e-a028-18b740563e96	\N	auth-cookie	973d96fb-e7bb-493a-b27f-0020b0da1731	a479d186-b6e0-4b5e-a4ae-17ca9299e441	2	10	f	\N	\N
d347d293-7c86-4c3d-a3bd-751259cc1a1f	\N	auth-spnego	973d96fb-e7bb-493a-b27f-0020b0da1731	a479d186-b6e0-4b5e-a4ae-17ca9299e441	3	20	f	\N	\N
fe1a4139-e3a7-40cc-a72a-a721f3d5b1f0	\N	identity-provider-redirector	973d96fb-e7bb-493a-b27f-0020b0da1731	a479d186-b6e0-4b5e-a4ae-17ca9299e441	2	25	f	\N	\N
f6574ce5-2003-4ef5-8543-7ec6a63bace8	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	a479d186-b6e0-4b5e-a4ae-17ca9299e441	2	30	t	8142e6f6-7ecb-4cf2-9dc0-4f969156b2ff	\N
cf2cecd6-9384-455c-a84f-900cc9c023ff	\N	auth-username-password-form	973d96fb-e7bb-493a-b27f-0020b0da1731	8142e6f6-7ecb-4cf2-9dc0-4f969156b2ff	0	10	f	\N	\N
8f0bbfc7-3860-4b4e-870f-9b8116d4ec06	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	8142e6f6-7ecb-4cf2-9dc0-4f969156b2ff	1	20	t	2a377040-5712-438f-b278-7f5896e3933f	\N
dcecff13-0bbd-416c-8d73-f89b27041d19	\N	conditional-user-configured	973d96fb-e7bb-493a-b27f-0020b0da1731	2a377040-5712-438f-b278-7f5896e3933f	0	10	f	\N	\N
c8bd1dc7-61b2-413f-8878-a2ebab8c2e59	\N	auth-otp-form	973d96fb-e7bb-493a-b27f-0020b0da1731	2a377040-5712-438f-b278-7f5896e3933f	0	20	f	\N	\N
d6e063c6-b3b0-4ec7-b679-db890f1befe8	\N	direct-grant-validate-username	973d96fb-e7bb-493a-b27f-0020b0da1731	280c5b66-d67d-4369-8fa8-130e7ac2bfa5	0	10	f	\N	\N
92a4fd1b-5c3d-4bed-a30e-e741e6a64157	\N	direct-grant-validate-password	973d96fb-e7bb-493a-b27f-0020b0da1731	280c5b66-d67d-4369-8fa8-130e7ac2bfa5	0	20	f	\N	\N
af9befdd-95f0-4854-a8fe-87b401686762	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	280c5b66-d67d-4369-8fa8-130e7ac2bfa5	1	30	t	994ba0fd-51e5-4dff-9afc-980ba8eb68cb	\N
db1bbeaa-df19-458d-a5af-167ed492464c	\N	conditional-user-configured	973d96fb-e7bb-493a-b27f-0020b0da1731	994ba0fd-51e5-4dff-9afc-980ba8eb68cb	0	10	f	\N	\N
40f91bcc-29b7-4199-a3c3-6c3baa09eea3	\N	direct-grant-validate-otp	973d96fb-e7bb-493a-b27f-0020b0da1731	994ba0fd-51e5-4dff-9afc-980ba8eb68cb	0	20	f	\N	\N
21d53102-0672-4152-9779-45bb6b640c40	\N	registration-page-form	973d96fb-e7bb-493a-b27f-0020b0da1731	60221bbd-51be-434f-a445-989857bd0d37	0	10	t	8757ec38-42db-4366-ae8c-06b47c54dcdb	\N
3847eaf2-dacb-4840-b87e-5513669e7077	\N	registration-user-creation	973d96fb-e7bb-493a-b27f-0020b0da1731	8757ec38-42db-4366-ae8c-06b47c54dcdb	0	20	f	\N	\N
8ac241dc-1ce6-403f-a8df-7d53695ac64d	\N	registration-password-action	973d96fb-e7bb-493a-b27f-0020b0da1731	8757ec38-42db-4366-ae8c-06b47c54dcdb	0	50	f	\N	\N
fc7f7651-9d23-452c-9d9e-ba3ab71e21ec	\N	registration-recaptcha-action	973d96fb-e7bb-493a-b27f-0020b0da1731	8757ec38-42db-4366-ae8c-06b47c54dcdb	3	60	f	\N	\N
546e7de0-f1a5-4de9-8454-abdb951e703f	\N	registration-terms-and-conditions	973d96fb-e7bb-493a-b27f-0020b0da1731	8757ec38-42db-4366-ae8c-06b47c54dcdb	3	70	f	\N	\N
a00134ea-f8f2-4f66-8ff7-08646214f2d0	\N	reset-credentials-choose-user	973d96fb-e7bb-493a-b27f-0020b0da1731	65760d3a-41d2-4746-b23a-8ee704a0fdc1	0	10	f	\N	\N
3e90df64-688f-4be6-9334-8a5eccff3a9c	\N	reset-credential-email	973d96fb-e7bb-493a-b27f-0020b0da1731	65760d3a-41d2-4746-b23a-8ee704a0fdc1	0	20	f	\N	\N
ffb65a8d-f184-4174-a50a-e153d10b1da0	\N	reset-password	973d96fb-e7bb-493a-b27f-0020b0da1731	65760d3a-41d2-4746-b23a-8ee704a0fdc1	0	30	f	\N	\N
c29bd982-9d6e-4b9b-bb2b-c6cd67e68d67	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	65760d3a-41d2-4746-b23a-8ee704a0fdc1	1	40	t	8bded140-d763-4478-b6ea-9b81d24bd01c	\N
77f2d96f-bac4-4470-b073-25031e52b788	\N	conditional-user-configured	973d96fb-e7bb-493a-b27f-0020b0da1731	8bded140-d763-4478-b6ea-9b81d24bd01c	0	10	f	\N	\N
c0dee756-74a6-4509-a57f-ec9d8b9a51d3	\N	reset-otp	973d96fb-e7bb-493a-b27f-0020b0da1731	8bded140-d763-4478-b6ea-9b81d24bd01c	0	20	f	\N	\N
c1af98ed-c19d-4e6a-83ac-8bb35ca8131d	\N	client-secret	973d96fb-e7bb-493a-b27f-0020b0da1731	9ad5afc2-6cba-44eb-a270-9270ecb49844	2	10	f	\N	\N
d15b894d-82b7-449b-914e-9401e7a42388	\N	client-jwt	973d96fb-e7bb-493a-b27f-0020b0da1731	9ad5afc2-6cba-44eb-a270-9270ecb49844	2	20	f	\N	\N
ee94055c-e6b1-4a4d-96ee-3f79f8774352	\N	client-secret-jwt	973d96fb-e7bb-493a-b27f-0020b0da1731	9ad5afc2-6cba-44eb-a270-9270ecb49844	2	30	f	\N	\N
870f15c3-fae7-4cfa-bb4b-9c3e8555f562	\N	client-x509	973d96fb-e7bb-493a-b27f-0020b0da1731	9ad5afc2-6cba-44eb-a270-9270ecb49844	2	40	f	\N	\N
3befe034-6976-4a4c-8a0b-28f9eb82c63b	\N	idp-review-profile	973d96fb-e7bb-493a-b27f-0020b0da1731	1c8acaea-694c-40e9-a8eb-dbba0dd402bc	0	10	f	\N	5115d123-ef2e-4ccc-adba-ccd6f09ce650
3dec7997-17e1-4fec-ace1-8e969b51e807	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	1c8acaea-694c-40e9-a8eb-dbba0dd402bc	0	20	t	7f5b49d8-eb02-4216-9a6d-027023b2cb4c	\N
709810a8-79d1-4d5d-84a8-172b2ed5d1bf	\N	idp-create-user-if-unique	973d96fb-e7bb-493a-b27f-0020b0da1731	7f5b49d8-eb02-4216-9a6d-027023b2cb4c	2	10	f	\N	f0c67198-3f11-4cb2-a508-e2e500a1e354
e18c8243-e123-42b3-a98f-88589b23ebc6	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	7f5b49d8-eb02-4216-9a6d-027023b2cb4c	2	20	t	2a1b6b93-25bd-4df3-a866-1fc0975e726f	\N
29c1e9cb-2d52-44c6-a041-9e0a6bb72520	\N	idp-confirm-link	973d96fb-e7bb-493a-b27f-0020b0da1731	2a1b6b93-25bd-4df3-a866-1fc0975e726f	0	10	f	\N	\N
596dbbae-6c55-4a94-9db2-c704a0c6b9cc	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	2a1b6b93-25bd-4df3-a866-1fc0975e726f	0	20	t	96483386-9608-4919-bc19-e3651ffcd226	\N
a3b93755-55d7-427b-a2d3-231d0f86f79c	\N	idp-email-verification	973d96fb-e7bb-493a-b27f-0020b0da1731	96483386-9608-4919-bc19-e3651ffcd226	2	10	f	\N	\N
15d4b7d4-0db5-4627-88c3-4c5fd19df895	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	96483386-9608-4919-bc19-e3651ffcd226	2	20	t	ff214bb3-cbdf-4ba7-921e-639b738580ab	\N
78717879-80e1-4a71-8d85-9addc697444a	\N	idp-username-password-form	973d96fb-e7bb-493a-b27f-0020b0da1731	ff214bb3-cbdf-4ba7-921e-639b738580ab	0	10	f	\N	\N
4d022388-b605-41c8-b158-17c5a0fdd7f4	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	ff214bb3-cbdf-4ba7-921e-639b738580ab	1	20	t	b050df6e-f75d-4db8-a61b-4c346693d5a8	\N
34ddc03b-8d9f-46a9-96c4-0ad0578399ad	\N	conditional-user-configured	973d96fb-e7bb-493a-b27f-0020b0da1731	b050df6e-f75d-4db8-a61b-4c346693d5a8	0	10	f	\N	\N
ae83ca17-9d2d-4f43-8d59-293a3ab89e43	\N	auth-otp-form	973d96fb-e7bb-493a-b27f-0020b0da1731	b050df6e-f75d-4db8-a61b-4c346693d5a8	0	20	f	\N	\N
e3ef5e75-8c57-475f-a6e2-3bb821a7ca48	\N	http-basic-authenticator	973d96fb-e7bb-493a-b27f-0020b0da1731	4ac73010-fb23-45b2-9fcb-dd3d1feec574	0	10	f	\N	\N
557dcfcb-2329-4168-b6a5-974f846abb8c	\N	docker-http-basic-authenticator	973d96fb-e7bb-493a-b27f-0020b0da1731	d322e97b-0cd3-4120-845f-69d22d19c92c	0	10	f	\N	\N
3f302278-7323-40f3-bbff-b64af582b66c	\N	auth-cookie	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	c450e768-7447-496c-8e82-605af43362d4	2	10	f	\N	\N
ef7ea9ef-9b57-4eef-8325-ae7831dfeaf2	\N	auth-spnego	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	c450e768-7447-496c-8e82-605af43362d4	3	20	f	\N	\N
bd8d4597-d266-4e00-9109-f152734d5fa5	\N	identity-provider-redirector	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	c450e768-7447-496c-8e82-605af43362d4	2	25	f	\N	\N
e9bd750f-14da-42aa-81ab-118280e942c0	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	c450e768-7447-496c-8e82-605af43362d4	2	30	t	2400b0b8-d343-4d14-b521-b260ec6db5c6	\N
23f6363e-a995-424a-9c64-d3ecc3c2cead	\N	auth-username-password-form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2400b0b8-d343-4d14-b521-b260ec6db5c6	0	10	f	\N	\N
bd1f6f52-a0ec-499c-af04-0b003c647d3e	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2400b0b8-d343-4d14-b521-b260ec6db5c6	1	20	t	7f7345be-553c-4fb9-9839-d7c32f80bff9	\N
2f0ff23f-dac3-44be-afd5-d16057bdc2cc	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	7f7345be-553c-4fb9-9839-d7c32f80bff9	0	10	f	\N	\N
2660dd20-ec84-4c2c-921b-bc901178cfd6	\N	auth-otp-form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	7f7345be-553c-4fb9-9839-d7c32f80bff9	0	20	f	\N	\N
8ff550a6-1a84-41be-88cb-627f30fc9b54	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	c450e768-7447-496c-8e82-605af43362d4	2	26	t	98a0e757-ed9a-4a94-8cc2-d1fbda9c8ab7	\N
e7bb3ae5-7b98-446e-823e-8c3fb94177ec	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	98a0e757-ed9a-4a94-8cc2-d1fbda9c8ab7	1	10	t	bb92d871-15b4-48fb-9816-a1ec55b939f0	\N
4ac85b93-9d20-4066-8754-f897d4ead96f	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	bb92d871-15b4-48fb-9816-a1ec55b939f0	0	10	f	\N	\N
abc1a176-245f-4e9f-8086-1743747a4a00	\N	organization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	bb92d871-15b4-48fb-9816-a1ec55b939f0	2	20	f	\N	\N
edc3e736-f38d-44ac-9c94-242ca73bbf98	\N	direct-grant-validate-username	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	557a4272-6efa-4fa9-a513-810d3a9c085f	0	10	f	\N	\N
078ab2d8-3d48-419b-96db-71e027e2f709	\N	direct-grant-validate-password	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	557a4272-6efa-4fa9-a513-810d3a9c085f	0	20	f	\N	\N
14a0505c-84e2-4ae7-ac23-e858cf233371	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	557a4272-6efa-4fa9-a513-810d3a9c085f	1	30	t	afae919d-7181-478f-b002-618a07d3cc0a	\N
c964734e-70cb-412b-83a1-58095f864081	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	afae919d-7181-478f-b002-618a07d3cc0a	0	10	f	\N	\N
6eafc736-1027-465e-a4ae-629834afb20d	\N	direct-grant-validate-otp	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	afae919d-7181-478f-b002-618a07d3cc0a	0	20	f	\N	\N
172f3f19-ac1c-4b14-97c9-b8899d1a2f07	\N	registration-page-form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2d4ddf33-0f2d-4717-8d7e-ada05a639e15	0	10	t	cd502660-19a6-4eba-a991-c91a5d9fe4a4	\N
ecbc1d61-082c-4921-b4b8-34b8232f2cd7	\N	registration-user-creation	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cd502660-19a6-4eba-a991-c91a5d9fe4a4	0	20	f	\N	\N
2b67ec5a-5066-4e10-b1c7-cec7d35271f4	\N	registration-password-action	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cd502660-19a6-4eba-a991-c91a5d9fe4a4	0	50	f	\N	\N
dcdc2084-7995-49f4-98ca-c863032181f4	\N	registration-recaptcha-action	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cd502660-19a6-4eba-a991-c91a5d9fe4a4	3	60	f	\N	\N
bb7517e6-5ae8-47f5-a183-6de35c6d7fc3	\N	registration-terms-and-conditions	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cd502660-19a6-4eba-a991-c91a5d9fe4a4	3	70	f	\N	\N
39e2f211-87a4-4859-9e4a-bb155bd535b5	\N	reset-credentials-choose-user	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	0	10	f	\N	\N
5d279a93-51bb-42c3-86ff-ea96e09a591d	\N	reset-credential-email	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	0	20	f	\N	\N
3398aea7-7185-4c4e-aabb-b55a74ce1189	\N	reset-password	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	0	30	f	\N	\N
be9ac214-90e3-428d-8700-49ec9f2035bc	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	1	40	t	bada9fae-1817-4e91-8539-354dd220ec51	\N
e2a25e70-6a51-41cc-bde9-a8d0644f623b	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	bada9fae-1817-4e91-8539-354dd220ec51	0	10	f	\N	\N
bd75a47e-22ad-4aba-80e3-ad1ca4a0290c	\N	reset-otp	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	bada9fae-1817-4e91-8539-354dd220ec51	0	20	f	\N	\N
c8cd5582-4243-439b-a00f-9ca97fb593ce	\N	client-secret	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e855670-895d-4717-8334-bca4b789cb71	2	10	f	\N	\N
70b6523c-c10a-427d-bf64-0f901ac77956	\N	client-jwt	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e855670-895d-4717-8334-bca4b789cb71	2	20	f	\N	\N
0bc2da38-b79f-4f67-99b1-cc82a086688b	\N	client-secret-jwt	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e855670-895d-4717-8334-bca4b789cb71	2	30	f	\N	\N
f6c7385e-b499-47ad-a10f-afe7680fdc29	\N	client-x509	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	9e855670-895d-4717-8334-bca4b789cb71	2	40	f	\N	\N
aaaa5646-d3fd-4740-a6f2-f7cdbc0bcc35	\N	idp-review-profile	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	8b0316a7-7c51-4276-8a55-50018c818a1e	0	10	f	\N	d416ee0b-a5f4-415d-90d8-19c99489ee4b
1485be81-6374-4ce7-a628-98d44047ff07	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	8b0316a7-7c51-4276-8a55-50018c818a1e	0	20	t	929cf540-65d5-46ad-9816-5eb9029d0392	\N
74f0d7a4-aa47-4b57-b17e-94f1213ee9b3	\N	idp-create-user-if-unique	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	929cf540-65d5-46ad-9816-5eb9029d0392	2	10	f	\N	56da6c9d-e1ce-455c-b229-537517223e38
e1c633b2-df8c-441c-a9cd-8b77ebfa5a90	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	929cf540-65d5-46ad-9816-5eb9029d0392	2	20	t	2c8ddbc6-29d1-4d79-9f5a-f126624c1804	\N
43ba3d37-6e3b-4ad0-97d7-1e494fc3992a	\N	idp-confirm-link	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8ddbc6-29d1-4d79-9f5a-f126624c1804	0	10	f	\N	\N
c77dd1ab-fe38-4f41-966f-83c8553bc0ea	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8ddbc6-29d1-4d79-9f5a-f126624c1804	0	20	t	b2e38170-beba-43ff-b538-3fffff45af8b	\N
51ab65d7-32c6-42a0-b7cb-3adbcb7e326b	\N	idp-email-verification	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	b2e38170-beba-43ff-b538-3fffff45af8b	2	10	f	\N	\N
a2ec873d-2ec5-4d53-bf8c-8f294e48226c	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	b2e38170-beba-43ff-b538-3fffff45af8b	2	20	t	cb858a75-5842-4fa8-8591-9653ac97fa73	\N
baf9114d-c02e-4d7a-8b48-4d515fca3d11	\N	idp-username-password-form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cb858a75-5842-4fa8-8591-9653ac97fa73	0	10	f	\N	\N
ee25034a-546c-42bd-9886-20ee5bddbea4	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cb858a75-5842-4fa8-8591-9653ac97fa73	1	20	t	b9ddd3d5-d9a5-4509-9cfc-4559d77a64a7	\N
ff043e1c-af26-4df9-99ee-0f6e444deb58	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	b9ddd3d5-d9a5-4509-9cfc-4559d77a64a7	0	10	f	\N	\N
d2b302c1-8f24-42c4-8034-59e2deecfba2	\N	auth-otp-form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	b9ddd3d5-d9a5-4509-9cfc-4559d77a64a7	0	20	f	\N	\N
e66165a7-6120-4d44-8da9-3523cea5c3aa	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	8b0316a7-7c51-4276-8a55-50018c818a1e	1	50	t	a61fca11-c2ce-459f-8f15-dd2a14bbe66b	\N
15167c49-c8cb-425c-ab1e-c50dccaea0e0	\N	conditional-user-configured	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	a61fca11-c2ce-459f-8f15-dd2a14bbe66b	0	10	f	\N	\N
d217a881-2547-4130-82f2-4dfb6f988708	\N	idp-add-organization-member	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	a61fca11-c2ce-459f-8f15-dd2a14bbe66b	0	20	f	\N	\N
0d0a3bcd-d523-4d3b-be5a-88047ffe2ace	\N	http-basic-authenticator	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cb247969-ce76-4bbf-9cea-b568d7b7622c	0	10	f	\N	\N
863fde24-8405-4dcf-b2a8-2d63a8b76490	\N	docker-http-basic-authenticator	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	5a3f71e0-8e4c-45be-a3a6-e7b9e59ab244	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
a479d186-b6e0-4b5e-a4ae-17ca9299e441	browser	Browser based authentication	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
8142e6f6-7ecb-4cf2-9dc0-4f969156b2ff	forms	Username, password, otp and other auth forms.	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
2a377040-5712-438f-b278-7f5896e3933f	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
280c5b66-d67d-4369-8fa8-130e7ac2bfa5	direct grant	OpenID Connect Resource Owner Grant	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
994ba0fd-51e5-4dff-9afc-980ba8eb68cb	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
60221bbd-51be-434f-a445-989857bd0d37	registration	Registration flow	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
8757ec38-42db-4366-ae8c-06b47c54dcdb	registration form	Registration form	973d96fb-e7bb-493a-b27f-0020b0da1731	form-flow	f	t
65760d3a-41d2-4746-b23a-8ee704a0fdc1	reset credentials	Reset credentials for a user if they forgot their password or something	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
8bded140-d763-4478-b6ea-9b81d24bd01c	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
9ad5afc2-6cba-44eb-a270-9270ecb49844	clients	Base authentication for clients	973d96fb-e7bb-493a-b27f-0020b0da1731	client-flow	t	t
1c8acaea-694c-40e9-a8eb-dbba0dd402bc	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
7f5b49d8-eb02-4216-9a6d-027023b2cb4c	User creation or linking	Flow for the existing/non-existing user alternatives	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
2a1b6b93-25bd-4df3-a866-1fc0975e726f	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
96483386-9608-4919-bc19-e3651ffcd226	Account verification options	Method with which to verity the existing account	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
ff214bb3-cbdf-4ba7-921e-639b738580ab	Verify Existing Account by Re-authentication	Reauthentication of existing account	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
b050df6e-f75d-4db8-a61b-4c346693d5a8	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	f	t
4ac73010-fb23-45b2-9fcb-dd3d1feec574	saml ecp	SAML ECP Profile Authentication Flow	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
d322e97b-0cd3-4120-845f-69d22d19c92c	docker auth	Used by Docker clients to authenticate against the IDP	973d96fb-e7bb-493a-b27f-0020b0da1731	basic-flow	t	t
c450e768-7447-496c-8e82-605af43362d4	browser	Browser based authentication	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
2400b0b8-d343-4d14-b521-b260ec6db5c6	forms	Username, password, otp and other auth forms.	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
7f7345be-553c-4fb9-9839-d7c32f80bff9	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
98a0e757-ed9a-4a94-8cc2-d1fbda9c8ab7	Organization	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
bb92d871-15b4-48fb-9816-a1ec55b939f0	Browser - Conditional Organization	Flow to determine if the organization identity-first login is to be used	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
557a4272-6efa-4fa9-a513-810d3a9c085f	direct grant	OpenID Connect Resource Owner Grant	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
afae919d-7181-478f-b002-618a07d3cc0a	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
2d4ddf33-0f2d-4717-8d7e-ada05a639e15	registration	Registration flow	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
cd502660-19a6-4eba-a991-c91a5d9fe4a4	registration form	Registration form	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	form-flow	f	t
9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	reset credentials	Reset credentials for a user if they forgot their password or something	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
bada9fae-1817-4e91-8539-354dd220ec51	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
9e855670-895d-4717-8334-bca4b789cb71	clients	Base authentication for clients	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	client-flow	t	t
8b0316a7-7c51-4276-8a55-50018c818a1e	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
929cf540-65d5-46ad-9816-5eb9029d0392	User creation or linking	Flow for the existing/non-existing user alternatives	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
2c8ddbc6-29d1-4d79-9f5a-f126624c1804	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
b2e38170-beba-43ff-b538-3fffff45af8b	Account verification options	Method with which to verity the existing account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
cb858a75-5842-4fa8-8591-9653ac97fa73	Verify Existing Account by Re-authentication	Reauthentication of existing account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
b9ddd3d5-d9a5-4509-9cfc-4559d77a64a7	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
a61fca11-c2ce-459f-8f15-dd2a14bbe66b	First Broker Login - Conditional Organization	Flow to determine if the authenticator that adds organization members is to be used	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	f	t
cb247969-ce76-4bbf-9cea-b568d7b7622c	saml ecp	SAML ECP Profile Authentication Flow	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
5a3f71e0-8e4c-45be-a3a6-e7b9e59ab244	docker auth	Used by Docker clients to authenticate against the IDP	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
5115d123-ef2e-4ccc-adba-ccd6f09ce650	review profile config	973d96fb-e7bb-493a-b27f-0020b0da1731
f0c67198-3f11-4cb2-a508-e2e500a1e354	create unique user config	973d96fb-e7bb-493a-b27f-0020b0da1731
d416ee0b-a5f4-415d-90d8-19c99489ee4b	review profile config	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43
56da6c9d-e1ce-455c-b229-537517223e38	create unique user config	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
5115d123-ef2e-4ccc-adba-ccd6f09ce650	missing	update.profile.on.first.login
f0c67198-3f11-4cb2-a508-e2e500a1e354	false	require.password.update.after.registration
56da6c9d-e1ce-455c-b229-537517223e38	false	require.password.update.after.registration
d416ee0b-a5f4-415d-90d8-19c99489ee4b	missing	update.profile.on.first.login
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
a4965dcb-4c1d-4086-a24e-69d6357f614b	t	f	master-realm	0	f	\N	\N	t	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
45110c87-41e9-4cf3-b4ed-958db7b94b24	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
ab322188-67b9-478c-9c88-4141b48a9e12	t	f	broker	0	f	\N	\N	t	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	t	t	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	t	t	admin-cli	0	t	\N	\N	f	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	f	quinta-ypua-realm	0	f	\N	\N	t	\N	f	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	0	f	f	quinta-ypua Realm	f	client-secret	\N	\N	\N	t	f	f	f
ba03050c-4820-4344-bf37-143e5570eb9f	t	f	realm-management	0	f	\N	\N	t	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	t	f	account	0	t	\N	/realms/quinta-ypua/account/	f	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	t	f	account-console	0	t	\N	/realms/quinta-ypua/account/	f	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
19ef52a7-5edc-4169-bf05-093c7a677859	t	f	broker	0	f	\N	\N	t	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	t	t	security-admin-console	0	t	\N	/admin/quinta-ypua/console/	f	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	t	t	admin-cli	0	t	\N	\N	f	\N	f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t	t	ypua-client-front	0	t	\N		f		f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	-1	t	f		f	client-secret			\N	t	f	t	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	t	t	quinta-ypua	0	f	Tseej5rltf97wJfbrp8WKnnbEU6VjtxI		f		f	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	openid-connect	-1	t	f		f	client-secret			\N	t	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
45110c87-41e9-4cf3-b4ed-958db7b94b24	post.logout.redirect.uris	+
f3b08e34-80ce-405d-b6e7-0c35eda81170	post.logout.redirect.uris	+
f3b08e34-80ce-405d-b6e7-0c35eda81170	pkce.code.challenge.method	S256
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	post.logout.redirect.uris	+
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	pkce.code.challenge.method	S256
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	client.use.lightweight.access.token.enabled	true
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	client.use.lightweight.access.token.enabled	true
069afb20-1212-44a1-bd8b-4ed84b6e9003	post.logout.redirect.uris	+
9ce4bbb9-10bf-4b13-8356-225e00c27007	post.logout.redirect.uris	+
9ce4bbb9-10bf-4b13-8356-225e00c27007	pkce.code.challenge.method	S256
bfcaa195-cce4-416f-8626-f0a3b93e381b	post.logout.redirect.uris	+
bfcaa195-cce4-416f-8626-f0a3b93e381b	pkce.code.challenge.method	S256
bfcaa195-cce4-416f-8626-f0a3b93e381b	client.use.lightweight.access.token.enabled	true
38b31b0d-6bab-4054-bed2-966dcd507b4c	client.use.lightweight.access.token.enabled	true
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	oauth2.device.authorization.grant.enabled	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	oidc.ciba.grant.enabled	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	backchannel.logout.session.required	true
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	backchannel.logout.revoke.offline.tokens	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	client.secret.creation.time	1773101769
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	realm_client	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	display.on.consent.screen	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	frontchannel.logout.session.required	true
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	login_theme	pousada
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	use.refresh.tokens	true
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	client_credentials.use_refresh_token	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	token.response.type.bearer.lower-case	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	tls.client.certificate.bound.access.tokens	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	require.pushed.authorization.requests	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	client.use.lightweight.access.token.enabled	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	client.introspection.response.allow.jwt.claim.enabled	false
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	acr.loa.map	{}
2c9ea497-af7f-4ac2-a6bb-831391b778f6	oauth2.device.authorization.grant.enabled	false
2c9ea497-af7f-4ac2-a6bb-831391b778f6	oidc.ciba.grant.enabled	false
2c9ea497-af7f-4ac2-a6bb-831391b778f6	backchannel.logout.session.required	true
2c9ea497-af7f-4ac2-a6bb-831391b778f6	backchannel.logout.revoke.offline.tokens	false
2c9ea497-af7f-4ac2-a6bb-831391b778f6	client.secret.creation.time	1778452605
2c9ea497-af7f-4ac2-a6bb-831391b778f6	realm_client	false
2c9ea497-af7f-4ac2-a6bb-831391b778f6	display.on.consent.screen	false
2c9ea497-af7f-4ac2-a6bb-831391b778f6	frontchannel.logout.session.required	true
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
e37042e7-33a2-4de2-bdd2-ec9f9c739814	offline_access	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect built-in scope: offline_access	openid-connect
62c0f6b5-5e6f-437b-9879-862536176c30	role_list	973d96fb-e7bb-493a-b27f-0020b0da1731	SAML role list	saml
133e76b9-1121-4c1d-aed9-24bf3a8ca799	saml_organization	973d96fb-e7bb-493a-b27f-0020b0da1731	Organization Membership	saml
1ee33799-b762-4af3-a32e-102ca6062249	profile	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect built-in scope: profile	openid-connect
4e5546b8-2e8d-435d-a164-19978835c118	email	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect built-in scope: email	openid-connect
0579c7ef-131c-44dd-9879-6d9fd5605507	address	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect built-in scope: address	openid-connect
405408a2-31c1-4e69-ae4d-edb75d4d335f	phone	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect built-in scope: phone	openid-connect
b1c35655-b9d3-426a-8b84-578aa7df9550	roles	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect scope for add user roles to the access token	openid-connect
5fa39419-de3c-4f95-8889-70143742d2ce	web-origins	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect scope for add allowed web origins to the access token	openid-connect
a9da574d-4857-4a68-93b8-1235e15fcccc	microprofile-jwt	973d96fb-e7bb-493a-b27f-0020b0da1731	Microprofile - JWT built-in scope	openid-connect
3ad51afb-b9b9-4451-9a1c-54a80c6678ba	acr	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
bab57543-7701-45d5-8ae3-82024fa8dd5a	basic	973d96fb-e7bb-493a-b27f-0020b0da1731	OpenID Connect scope for add all basic claims to the token	openid-connect
14548990-f86f-4a01-aa96-334bf82c6ba2	service_account	973d96fb-e7bb-493a-b27f-0020b0da1731	Specific scope for a client enabled for service accounts	openid-connect
7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	organization	973d96fb-e7bb-493a-b27f-0020b0da1731	Additional claims about the organization a subject belongs to	openid-connect
afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	offline_access	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect built-in scope: offline_access	openid-connect
070dcf44-8b37-4d44-ba36-180871706c7b	role_list	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	SAML role list	saml
69508c30-cabe-4cd8-b8ea-d1067db97f42	saml_organization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	Organization Membership	saml
dc4f3172-7fa8-4a40-9f96-871d134212ce	profile	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect built-in scope: profile	openid-connect
f05f9faf-6a40-4228-8afd-3bb9eadefa9a	email	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect built-in scope: email	openid-connect
34005ce2-fcdb-45e3-a007-d3fb35796dfd	address	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect built-in scope: address	openid-connect
38635cee-3db8-49e7-bb41-37b4b70ffefd	phone	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect built-in scope: phone	openid-connect
cf806293-5721-4f38-9044-726dd7b53b62	roles	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect scope for add user roles to the access token	openid-connect
73fbb70d-f9b2-4d22-bd90-91a1c580202f	web-origins	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect scope for add allowed web origins to the access token	openid-connect
73475f69-7535-4746-b4c6-af4db747fb8a	microprofile-jwt	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	Microprofile - JWT built-in scope	openid-connect
7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	acr	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
4c0c22e0-1a30-4ccb-8a60-8552041ab07a	basic	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	OpenID Connect scope for add all basic claims to the token	openid-connect
1efe54a4-0d74-42a0-b30e-9261d0d3626b	service_account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	Specific scope for a client enabled for service accounts	openid-connect
20658153-e9d8-4fdd-bdee-764461e3446e	organization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	Additional claims about the organization a subject belongs to	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
e37042e7-33a2-4de2-bdd2-ec9f9c739814	true	display.on.consent.screen
e37042e7-33a2-4de2-bdd2-ec9f9c739814	${offlineAccessScopeConsentText}	consent.screen.text
62c0f6b5-5e6f-437b-9879-862536176c30	true	display.on.consent.screen
62c0f6b5-5e6f-437b-9879-862536176c30	${samlRoleListScopeConsentText}	consent.screen.text
133e76b9-1121-4c1d-aed9-24bf3a8ca799	false	display.on.consent.screen
1ee33799-b762-4af3-a32e-102ca6062249	true	display.on.consent.screen
1ee33799-b762-4af3-a32e-102ca6062249	${profileScopeConsentText}	consent.screen.text
1ee33799-b762-4af3-a32e-102ca6062249	true	include.in.token.scope
4e5546b8-2e8d-435d-a164-19978835c118	true	display.on.consent.screen
4e5546b8-2e8d-435d-a164-19978835c118	${emailScopeConsentText}	consent.screen.text
4e5546b8-2e8d-435d-a164-19978835c118	true	include.in.token.scope
0579c7ef-131c-44dd-9879-6d9fd5605507	true	display.on.consent.screen
0579c7ef-131c-44dd-9879-6d9fd5605507	${addressScopeConsentText}	consent.screen.text
0579c7ef-131c-44dd-9879-6d9fd5605507	true	include.in.token.scope
405408a2-31c1-4e69-ae4d-edb75d4d335f	true	display.on.consent.screen
405408a2-31c1-4e69-ae4d-edb75d4d335f	${phoneScopeConsentText}	consent.screen.text
405408a2-31c1-4e69-ae4d-edb75d4d335f	true	include.in.token.scope
b1c35655-b9d3-426a-8b84-578aa7df9550	true	display.on.consent.screen
b1c35655-b9d3-426a-8b84-578aa7df9550	${rolesScopeConsentText}	consent.screen.text
b1c35655-b9d3-426a-8b84-578aa7df9550	false	include.in.token.scope
5fa39419-de3c-4f95-8889-70143742d2ce	false	display.on.consent.screen
5fa39419-de3c-4f95-8889-70143742d2ce		consent.screen.text
5fa39419-de3c-4f95-8889-70143742d2ce	false	include.in.token.scope
a9da574d-4857-4a68-93b8-1235e15fcccc	false	display.on.consent.screen
a9da574d-4857-4a68-93b8-1235e15fcccc	true	include.in.token.scope
3ad51afb-b9b9-4451-9a1c-54a80c6678ba	false	display.on.consent.screen
3ad51afb-b9b9-4451-9a1c-54a80c6678ba	false	include.in.token.scope
bab57543-7701-45d5-8ae3-82024fa8dd5a	false	display.on.consent.screen
bab57543-7701-45d5-8ae3-82024fa8dd5a	false	include.in.token.scope
14548990-f86f-4a01-aa96-334bf82c6ba2	false	display.on.consent.screen
14548990-f86f-4a01-aa96-334bf82c6ba2	false	include.in.token.scope
7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	true	display.on.consent.screen
7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	${organizationScopeConsentText}	consent.screen.text
7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	true	include.in.token.scope
afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	true	display.on.consent.screen
afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	${offlineAccessScopeConsentText}	consent.screen.text
070dcf44-8b37-4d44-ba36-180871706c7b	true	display.on.consent.screen
070dcf44-8b37-4d44-ba36-180871706c7b	${samlRoleListScopeConsentText}	consent.screen.text
69508c30-cabe-4cd8-b8ea-d1067db97f42	false	display.on.consent.screen
dc4f3172-7fa8-4a40-9f96-871d134212ce	true	display.on.consent.screen
dc4f3172-7fa8-4a40-9f96-871d134212ce	${profileScopeConsentText}	consent.screen.text
dc4f3172-7fa8-4a40-9f96-871d134212ce	true	include.in.token.scope
f05f9faf-6a40-4228-8afd-3bb9eadefa9a	true	display.on.consent.screen
f05f9faf-6a40-4228-8afd-3bb9eadefa9a	${emailScopeConsentText}	consent.screen.text
f05f9faf-6a40-4228-8afd-3bb9eadefa9a	true	include.in.token.scope
34005ce2-fcdb-45e3-a007-d3fb35796dfd	true	display.on.consent.screen
34005ce2-fcdb-45e3-a007-d3fb35796dfd	${addressScopeConsentText}	consent.screen.text
34005ce2-fcdb-45e3-a007-d3fb35796dfd	true	include.in.token.scope
38635cee-3db8-49e7-bb41-37b4b70ffefd	true	display.on.consent.screen
38635cee-3db8-49e7-bb41-37b4b70ffefd	${phoneScopeConsentText}	consent.screen.text
38635cee-3db8-49e7-bb41-37b4b70ffefd	true	include.in.token.scope
cf806293-5721-4f38-9044-726dd7b53b62	true	display.on.consent.screen
cf806293-5721-4f38-9044-726dd7b53b62	${rolesScopeConsentText}	consent.screen.text
cf806293-5721-4f38-9044-726dd7b53b62	false	include.in.token.scope
73fbb70d-f9b2-4d22-bd90-91a1c580202f	false	display.on.consent.screen
73fbb70d-f9b2-4d22-bd90-91a1c580202f		consent.screen.text
73fbb70d-f9b2-4d22-bd90-91a1c580202f	false	include.in.token.scope
73475f69-7535-4746-b4c6-af4db747fb8a	false	display.on.consent.screen
73475f69-7535-4746-b4c6-af4db747fb8a	true	include.in.token.scope
7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	false	display.on.consent.screen
7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	false	include.in.token.scope
4c0c22e0-1a30-4ccb-8a60-8552041ab07a	false	display.on.consent.screen
4c0c22e0-1a30-4ccb-8a60-8552041ab07a	false	include.in.token.scope
1efe54a4-0d74-42a0-b30e-9261d0d3626b	false	display.on.consent.screen
1efe54a4-0d74-42a0-b30e-9261d0d3626b	false	include.in.token.scope
20658153-e9d8-4fdd-bdee-764461e3446e	true	display.on.consent.screen
20658153-e9d8-4fdd-bdee-764461e3446e	${organizationScopeConsentText}	consent.screen.text
20658153-e9d8-4fdd-bdee-764461e3446e	true	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
45110c87-41e9-4cf3-b4ed-958db7b94b24	b1c35655-b9d3-426a-8b84-578aa7df9550	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	5fa39419-de3c-4f95-8889-70143742d2ce	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	4e5546b8-2e8d-435d-a164-19978835c118	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	1ee33799-b762-4af3-a32e-102ca6062249	t
45110c87-41e9-4cf3-b4ed-958db7b94b24	0579c7ef-131c-44dd-9879-6d9fd5605507	f
45110c87-41e9-4cf3-b4ed-958db7b94b24	a9da574d-4857-4a68-93b8-1235e15fcccc	f
45110c87-41e9-4cf3-b4ed-958db7b94b24	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
45110c87-41e9-4cf3-b4ed-958db7b94b24	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
45110c87-41e9-4cf3-b4ed-958db7b94b24	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	b1c35655-b9d3-426a-8b84-578aa7df9550	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	5fa39419-de3c-4f95-8889-70143742d2ce	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	4e5546b8-2e8d-435d-a164-19978835c118	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	1ee33799-b762-4af3-a32e-102ca6062249	t
f3b08e34-80ce-405d-b6e7-0c35eda81170	0579c7ef-131c-44dd-9879-6d9fd5605507	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	a9da574d-4857-4a68-93b8-1235e15fcccc	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
f3b08e34-80ce-405d-b6e7-0c35eda81170	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	b1c35655-b9d3-426a-8b84-578aa7df9550	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	5fa39419-de3c-4f95-8889-70143742d2ce	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	4e5546b8-2e8d-435d-a164-19978835c118	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	1ee33799-b762-4af3-a32e-102ca6062249	t
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	0579c7ef-131c-44dd-9879-6d9fd5605507	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	a9da574d-4857-4a68-93b8-1235e15fcccc	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
e4fa6d43-2cf5-4d5e-8f3d-1aff2cb5fb6a	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
ab322188-67b9-478c-9c88-4141b48a9e12	b1c35655-b9d3-426a-8b84-578aa7df9550	t
ab322188-67b9-478c-9c88-4141b48a9e12	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
ab322188-67b9-478c-9c88-4141b48a9e12	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
ab322188-67b9-478c-9c88-4141b48a9e12	5fa39419-de3c-4f95-8889-70143742d2ce	t
ab322188-67b9-478c-9c88-4141b48a9e12	4e5546b8-2e8d-435d-a164-19978835c118	t
ab322188-67b9-478c-9c88-4141b48a9e12	1ee33799-b762-4af3-a32e-102ca6062249	t
ab322188-67b9-478c-9c88-4141b48a9e12	0579c7ef-131c-44dd-9879-6d9fd5605507	f
ab322188-67b9-478c-9c88-4141b48a9e12	a9da574d-4857-4a68-93b8-1235e15fcccc	f
ab322188-67b9-478c-9c88-4141b48a9e12	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
ab322188-67b9-478c-9c88-4141b48a9e12	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
ab322188-67b9-478c-9c88-4141b48a9e12	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
a4965dcb-4c1d-4086-a24e-69d6357f614b	b1c35655-b9d3-426a-8b84-578aa7df9550	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	5fa39419-de3c-4f95-8889-70143742d2ce	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	4e5546b8-2e8d-435d-a164-19978835c118	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	1ee33799-b762-4af3-a32e-102ca6062249	t
a4965dcb-4c1d-4086-a24e-69d6357f614b	0579c7ef-131c-44dd-9879-6d9fd5605507	f
a4965dcb-4c1d-4086-a24e-69d6357f614b	a9da574d-4857-4a68-93b8-1235e15fcccc	f
a4965dcb-4c1d-4086-a24e-69d6357f614b	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
a4965dcb-4c1d-4086-a24e-69d6357f614b	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
a4965dcb-4c1d-4086-a24e-69d6357f614b	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	b1c35655-b9d3-426a-8b84-578aa7df9550	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	5fa39419-de3c-4f95-8889-70143742d2ce	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	4e5546b8-2e8d-435d-a164-19978835c118	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	1ee33799-b762-4af3-a32e-102ca6062249	t
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	0579c7ef-131c-44dd-9879-6d9fd5605507	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	a9da574d-4857-4a68-93b8-1235e15fcccc	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	cf806293-5721-4f38-9044-726dd7b53b62	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
069afb20-1212-44a1-bd8b-4ed84b6e9003	73475f69-7535-4746-b4c6-af4db747fb8a	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
069afb20-1212-44a1-bd8b-4ed84b6e9003	20658153-e9d8-4fdd-bdee-764461e3446e	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	cf806293-5721-4f38-9044-726dd7b53b62	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
9ce4bbb9-10bf-4b13-8356-225e00c27007	73475f69-7535-4746-b4c6-af4db747fb8a	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
9ce4bbb9-10bf-4b13-8356-225e00c27007	20658153-e9d8-4fdd-bdee-764461e3446e	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	cf806293-5721-4f38-9044-726dd7b53b62	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
38b31b0d-6bab-4054-bed2-966dcd507b4c	73475f69-7535-4746-b4c6-af4db747fb8a	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
38b31b0d-6bab-4054-bed2-966dcd507b4c	20658153-e9d8-4fdd-bdee-764461e3446e	f
19ef52a7-5edc-4169-bf05-093c7a677859	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
19ef52a7-5edc-4169-bf05-093c7a677859	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
19ef52a7-5edc-4169-bf05-093c7a677859	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
19ef52a7-5edc-4169-bf05-093c7a677859	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
19ef52a7-5edc-4169-bf05-093c7a677859	cf806293-5721-4f38-9044-726dd7b53b62	t
19ef52a7-5edc-4169-bf05-093c7a677859	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
19ef52a7-5edc-4169-bf05-093c7a677859	73475f69-7535-4746-b4c6-af4db747fb8a	f
19ef52a7-5edc-4169-bf05-093c7a677859	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
19ef52a7-5edc-4169-bf05-093c7a677859	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
19ef52a7-5edc-4169-bf05-093c7a677859	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
19ef52a7-5edc-4169-bf05-093c7a677859	20658153-e9d8-4fdd-bdee-764461e3446e	f
ba03050c-4820-4344-bf37-143e5570eb9f	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
ba03050c-4820-4344-bf37-143e5570eb9f	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
ba03050c-4820-4344-bf37-143e5570eb9f	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
ba03050c-4820-4344-bf37-143e5570eb9f	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
ba03050c-4820-4344-bf37-143e5570eb9f	cf806293-5721-4f38-9044-726dd7b53b62	t
ba03050c-4820-4344-bf37-143e5570eb9f	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
ba03050c-4820-4344-bf37-143e5570eb9f	73475f69-7535-4746-b4c6-af4db747fb8a	f
ba03050c-4820-4344-bf37-143e5570eb9f	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
ba03050c-4820-4344-bf37-143e5570eb9f	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
ba03050c-4820-4344-bf37-143e5570eb9f	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
ba03050c-4820-4344-bf37-143e5570eb9f	20658153-e9d8-4fdd-bdee-764461e3446e	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	cf806293-5721-4f38-9044-726dd7b53b62	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
bfcaa195-cce4-416f-8626-f0a3b93e381b	73475f69-7535-4746-b4c6-af4db747fb8a	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
bfcaa195-cce4-416f-8626-f0a3b93e381b	20658153-e9d8-4fdd-bdee-764461e3446e	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	cf806293-5721-4f38-9044-726dd7b53b62	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	73475f69-7535-4746-b4c6-af4db747fb8a	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	20658153-e9d8-4fdd-bdee-764461e3446e	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	cf806293-5721-4f38-9044-726dd7b53b62	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
2c9ea497-af7f-4ac2-a6bb-831391b778f6	73475f69-7535-4746-b4c6-af4db747fb8a	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
2c9ea497-af7f-4ac2-a6bb-831391b778f6	20658153-e9d8-4fdd-bdee-764461e3446e	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
e37042e7-33a2-4de2-bdd2-ec9f9c739814	06fe37f5-4b92-45e5-80ad-498929412bfb
afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	cf64d75b-bb8f-4540-b035-bd0b1442c137
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
bf63e433-757b-454f-ad1d-37afdc649099	Trusted Hosts	973d96fb-e7bb-493a-b27f-0020b0da1731	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
fb1be28d-90b0-4572-a0ce-7bf63a47f161	Consent Required	973d96fb-e7bb-493a-b27f-0020b0da1731	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
079cf8ac-c0df-4d61-a1c5-710664ad220f	Full Scope Disabled	973d96fb-e7bb-493a-b27f-0020b0da1731	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
2ba3ab18-af7c-4fab-a2e0-416622b0fecb	Max Clients Limit	973d96fb-e7bb-493a-b27f-0020b0da1731	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
67495fb9-44a5-49fb-b671-cd4bb55292a0	Allowed Protocol Mapper Types	973d96fb-e7bb-493a-b27f-0020b0da1731	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
2df831e4-6c4b-49b4-97f7-c1be1bfa69a2	Allowed Client Scopes	973d96fb-e7bb-493a-b27f-0020b0da1731	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	anonymous
7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	Allowed Protocol Mapper Types	973d96fb-e7bb-493a-b27f-0020b0da1731	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	authenticated
b442f6d9-1ddb-48b9-a747-c3fb461d62a1	Allowed Client Scopes	973d96fb-e7bb-493a-b27f-0020b0da1731	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	authenticated
9491f4c3-5f17-4a17-9bb1-c0b324265d61	rsa-generated	973d96fb-e7bb-493a-b27f-0020b0da1731	rsa-generated	org.keycloak.keys.KeyProvider	973d96fb-e7bb-493a-b27f-0020b0da1731	\N
c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	rsa-enc-generated	973d96fb-e7bb-493a-b27f-0020b0da1731	rsa-enc-generated	org.keycloak.keys.KeyProvider	973d96fb-e7bb-493a-b27f-0020b0da1731	\N
0e00f6c2-0975-4cc1-861c-78b49d62cb3b	hmac-generated-hs512	973d96fb-e7bb-493a-b27f-0020b0da1731	hmac-generated	org.keycloak.keys.KeyProvider	973d96fb-e7bb-493a-b27f-0020b0da1731	\N
22201947-e7bd-43e8-a6cc-eb6dac1b97dd	aes-generated	973d96fb-e7bb-493a-b27f-0020b0da1731	aes-generated	org.keycloak.keys.KeyProvider	973d96fb-e7bb-493a-b27f-0020b0da1731	\N
33e8a9c9-dee9-45cc-8f07-217dc402c896	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	973d96fb-e7bb-493a-b27f-0020b0da1731	\N
e6f3fce2-d8d0-49ac-8b77-b81302539072	rsa-generated	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	rsa-generated	org.keycloak.keys.KeyProvider	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N
7af3aa86-e155-4b4e-b62d-5537261645fc	rsa-enc-generated	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	rsa-enc-generated	org.keycloak.keys.KeyProvider	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N
d0836b85-4031-4ee0-980f-3a2242dd1e92	hmac-generated-hs512	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	hmac-generated	org.keycloak.keys.KeyProvider	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N
f5479ef5-d439-4ea6-8758-c295619b6a50	aes-generated	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	aes-generated	org.keycloak.keys.KeyProvider	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N
74c5892d-172e-4a13-ac88-1b0072809699	Trusted Hosts	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
042890ad-0870-41b1-9b17-7cd55eebca96	Consent Required	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
b0809af2-45a5-49d4-a0d0-e9c8d950e763	Full Scope Disabled	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
b353e6eb-d688-4800-bfde-d415a352f130	Max Clients Limit	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
6b61d3d2-e33f-4f6f-91d2-661092d97431	Allowed Protocol Mapper Types	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
9e6684b9-e5e4-4a23-a81f-7d65f41b01a5	Allowed Client Scopes	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	anonymous
a086671a-068f-4e10-a1dd-0d7e3b222e4c	Allowed Protocol Mapper Types	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	authenticated
7f11e88d-3cb9-43fa-a555-c831b8e5595b	Allowed Client Scopes	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	authenticated
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
cadeaf20-72f8-4164-b3d6-f1a76e72ee41	bf63e433-757b-454f-ad1d-37afdc649099	client-uris-must-match	true
4c059b8c-e3a2-4823-b019-ef1e1d965a5b	bf63e433-757b-454f-ad1d-37afdc649099	host-sending-registration-request-must-match	true
b3fbbf7c-1b8a-4192-ab60-2e5e29f7bbc0	b442f6d9-1ddb-48b9-a747-c3fb461d62a1	allow-default-scopes	true
c207ebac-1eaa-42db-8ac4-a4cd3fc71358	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	oidc-full-name-mapper
b382ebe3-e3ef-440e-853b-17e2c4b18c31	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
ddce0532-3f0f-4822-bae3-47e77504dc1a	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
c19f8b98-37ee-4197-b2a0-e1c37af7e8ba	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	saml-user-attribute-mapper
f86a54e0-d416-4a90-81ce-607a4061a866	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
42cbaa81-9001-4798-850a-d8eed71eb160	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	saml-role-list-mapper
1278edac-95db-4915-9672-b315d60a0362	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	saml-user-property-mapper
e9544063-d2a8-4d14-8131-54057abe4e82	67495fb9-44a5-49fb-b671-cd4bb55292a0	allowed-protocol-mapper-types	oidc-address-mapper
9a40eea9-db3b-4938-8954-e2591eb30275	2ba3ab18-af7c-4fab-a2e0-416622b0fecb	max-clients	200
52167c64-e1a6-4ac5-a3f7-89f4b1d1db83	2df831e4-6c4b-49b4-97f7-c1be1bfa69a2	allow-default-scopes	true
42c8c573-fd15-439e-9361-d7a741541268	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	saml-user-attribute-mapper
e513b2cb-e960-49bd-aa17-632e3691ec35	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
f418dc9a-a004-49e9-9abb-6eccf1898312	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
b7afa331-6111-4a49-b76c-c524f566952c	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	oidc-full-name-mapper
ff3ab3d9-28b8-4814-9533-99d9bf625cc2	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
c25155a3-b83e-4aa5-a2c7-d45eb5380afd	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	saml-user-property-mapper
de942c2a-94aa-4fd9-8aef-9b4c3d182de6	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	oidc-address-mapper
c91590af-9791-4edc-ba0c-74027c3d12c1	7eeaa9d3-f528-4c5f-b541-4ab0e32e6ea8	allowed-protocol-mapper-types	saml-role-list-mapper
bd3304b3-c52a-411d-9220-ff2737d15ac2	33e8a9c9-dee9-45cc-8f07-217dc402c896	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"firstName","displayName":"${firstName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"lastName","displayName":"${lastName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
4742e5d3-66d8-4bea-90d4-6bfff9872628	c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	priority	100
7d520228-be4a-46d7-88ff-809a3a5f1e87	c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	certificate	MIICmzCCAYMCBgGc1NnREzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjYwMzA5MjMwNDMwWhcNMzYwMzA5MjMwNjEwWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC35vbIiTKVEynkSb/A9VUlXePZ4Tzt4nsgvf0m2o8TCe6FALclq2o25KGH/26gn45Vqx+ZDH47q5qv1PTNo9y+iNAM4YIiXt7CRlAGUF9iA3TKSZpO6AqpD1CFdFzHF8RTwI97kvi1yo5vvzE4InLw3cEnnrg9ojTS7K+fmhGgW2D7N9tLKBzkqArpTFNZYi/0cEhuVnw3U1weygaAEjmUqu5SXaXh0sILBbpNut2/rIK0W/fvwKnGsWiNpCXVsA44uYhl/q4y9xwGiU+fryX3RpiwQoFSkdytXJTnEUDJL9nraP/Xmicjfmzo0hVDEbORrtHySu8RqWegand7RVk9AgMBAAEwDQYJKoZIhvcNAQELBQADggEBAH2PfScVfRXHUXiAF+X1W86+wvmGXFf9tx/dReuqN6WBptltDGHZFsacp5JMPbc0jE+T5eqO8phS/94ljoRphLtMcCyBoyvTsO6Ky2ZkstOinLrjUbHj/z65zD0IYuCNvBBI4nuER5nT5VgSQmrHzgWnCyDHFebEM47qxPnBNNTKbJf4FxKa2tTx5LYieOm2pEdRPTvvHej4BpPhNC1deYu7rpZxM2rvj0GXVSQDxUrYmAIh3pRQ+Q0xQrNoOwbUC21feFeqVf57cZLVWisVB++PDHJdHlH2E3LdAqABMBx9OULFvgNNsOra31WWHMoi/GIMXFk7mdt4xEB+NWZ06Cw=
81f98189-0e25-48e3-8d8b-37e188a9de83	c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	privateKey	MIIEpAIBAAKCAQEAt+b2yIkylRMp5Em/wPVVJV3j2eE87eJ7IL39JtqPEwnuhQC3JatqNuShh/9uoJ+OVasfmQx+O6uar9T0zaPcvojQDOGCIl7ewkZQBlBfYgN0ykmaTugKqQ9QhXRcxxfEU8CPe5L4tcqOb78xOCJy8N3BJ564PaI00uyvn5oRoFtg+zfbSygc5KgK6UxTWWIv9HBIblZ8N1NcHsoGgBI5lKruUl2l4dLCCwW6Tbrdv6yCtFv378CpxrFojaQl1bAOOLmIZf6uMvccBolPn68l90aYsEKBUpHcrVyU5xFAyS/Z62j/15onI35s6NIVQxGzka7R8krvEalnoGp3e0VZPQIDAQABAoIBACVF77DXdeQXHT+C3xWZ4+cN75eCelnwtGn/d+a/F46RTcGxUm2VH5L+peqtj9uIW03rwBzCFne7oT+QFwny81O+4QQrVLfoqJeRrriI47w23R3+6wKhLz/ytaxG2GMV4xnihnJSaIxYAKSPiMdK3CcLmxGPruH3FWPGyxW7IWVxpy47Qrxpo69AgMjTqAE7g2KVV5hS45I7fOcHYooEgNUsA1Nmqj/RmNQdUkw9iEQyoLZ4VGtoS5nKwrdFmqUHj7RvxDZt9t2Guoe1vUgGk/2fSk2Rnp6ZnTGRZ+AdoX3PvG1zQhwPa1QIaDEZ1aVC2Oj1JLZckTpWStDfw3wup5UCgYEA953peE2vVoyDH52joCEp4B7PhdCuXun0GNDRY3lHg1VJY8Ia/aLguh2OoePUJXn7Pj7o9RZRhQlaEF1rsBhPlB3ZYbkH+f2Ip6nwosfM9JmGulMkG4PGvHB+FV6iyP23khsQX9GS7xjgSWF7G3Xg499Phbex0wfa5Jdxu/3Ui1cCgYEAviDWzMPqhU0FLRsLaxplIN1rXwo65dU5gUaaf2wH4vKGNS1fPtV39tGm2GXlUolccyKw9LVNoMcCV4NgVjxhXl8cgDaMaLU3uAUXx3anvP5THSg3berZn34EzcofYVByjUnd8XbNWsgS8lW2PvNIjVM9rFUXvUuYBls8X3HCN4sCgYBSMkY9HDSXG1+vkAmv7zJSZofm2MX5VdoknrTTO4AktZv+FIIXLglBXuxpCxLyOdLP79wu93CqI9eU1R4ZP4izF1GA4gYm0yWY2o25y29axtf1ZAH5bD39ca4488g/xhE42+kMlaob2nshgM136SJ1Ijzom3jZH6Nm/GrRx8nVUwKBgQC4QyVJ4Bn6xLFejJZdX4BhAJZdzpFUS3jm1CFJms5t4XO2N9nWEJaqK3eSw/YjLqLdpuJu1wN77UNUwytriCsphe4eSD99/xsYCj+IfXOFU8oDOQW3TmNJEp3fP6N38bqDOfKSYPwWnk7Wc8Ejcy5+YQ0lZnaSa5/TkTqKmbqLjwKBgQDlI2gxRHgTnU1Nz1MkQGyBXi4fI12LgKuWtZTou+XAUzuHCmeExjvpxy2NyR0lBYMwseVoJNsohP5nxR44SryuF5R14O+rGVC/1bSf0q+tCeNxdjPmSK0ghzwgEd0Fc/Sid4XDkgf2s1L0TrSQyIZFNB5jDev8qBGSFFtgiPIHWg==
020c36e2-076c-41a0-97ec-eec298ba8292	c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	keyUse	ENC
4e9e2d5f-229d-4c81-92da-79b3ce2de135	c436b6fb-f66f-4d78-ac9d-4b42dbb03e1c	algorithm	RSA-OAEP
b704f6e1-1cd9-4a36-84d5-56d6a88cee04	9491f4c3-5f17-4a17-9bb1-c0b324265d61	keyUse	SIG
26506ef1-c623-4e39-a0d4-9745e8a06884	e6f3fce2-d8d0-49ac-8b77-b81302539072	keyUse	SIG
8d266b05-723b-4865-9a8b-7cf463729870	e6f3fce2-d8d0-49ac-8b77-b81302539072	priority	100
c435b911-4650-4018-932a-7b4af152afe4	9491f4c3-5f17-4a17-9bb1-c0b324265d61	certificate	MIICmzCCAYMCBgGc1NnQlTANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjYwMzA5MjMwNDMwWhcNMzYwMzA5MjMwNjEwWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDms8pXGjSRebABn7WrWwORHBUK193/aCXAkl/lX2Cmjm34F10oAqJvidIo8EDk/B3ISII6eDq6+zly3aDFHmRVemd29dPM9Cd9ZNZBJiota+UXxC8vYca1UmzUm/adeHJh/5o906qzx8Y9aDBHNYZGMkzJN0bm2dpxP1Xg9rBKN7U+p9JupiaTNRlO266WMCZKUOhp1+xcEnLsu6ON1iqA4z+tZH7UgPqwtpFtCREuFaLXEzgpnMr92+hFwyFhxrgdkoSB8MKeTwBmYcgIRZayIgHLtNKwhsSEmMrhx0fRj5Sz+mmOzlxGafyXKA5iv8vljmWfps9D26guiZEDRF8XAgMBAAEwDQYJKoZIhvcNAQELBQADggEBABdlvWb4ombdDnPusSblXHIPYIn8qjl6bxA80NdTpXmWVteVRPQH+Tt7m5F9kD5x9JE44m70DY1kH45SvIj5Lp83pMpbUt6oQsYIggifK6UbNB8iEUnczUYmUIEiLVTjaLH0tCqd3EYNYiXlVhErVgGE/0Q2WiQ0w/UHtAfP5pdRAvUrdFM2DZfMM2yVxEFRcEUx3o7ItnIfdA1Nd7yYnY3AOmLwvS9PKjnpegLlOr0+ZwRf+BQ6eikYbEyBoSZpPvflvnveJZDhjfEmA7bkoMDK1Uz8VTQAXQPZPJG51PNqAlv3iTzHlIg8tBUUPscXwMcDoEoiSByVFnGUBbg5sFE=
48fae12c-a3b0-43cf-8cf7-91cecaa7f380	9491f4c3-5f17-4a17-9bb1-c0b324265d61	privateKey	MIIEowIBAAKCAQEA5rPKVxo0kXmwAZ+1q1sDkRwVCtfd/2glwJJf5V9gpo5t+BddKAKib4nSKPBA5PwdyEiCOng6uvs5ct2gxR5kVXpndvXTzPQnfWTWQSYqLWvlF8QvL2HGtVJs1Jv2nXhyYf+aPdOqs8fGPWgwRzWGRjJMyTdG5tnacT9V4PawSje1PqfSbqYmkzUZTtuuljAmSlDoadfsXBJy7LujjdYqgOM/rWR+1ID6sLaRbQkRLhWi1xM4KZzK/dvoRcMhYca4HZKEgfDCnk8AZmHICEWWsiIBy7TSsIbEhJjK4cdH0Y+Us/ppjs5cRmn8lygOYr/L5Y5ln6bPQ9uoLomRA0RfFwIDAQABAoIBAFPv4TQcvDibui51Ec+VLzCKM5u827AUD/xHJW3abFtdIeSsd3/ZqYkEb3rmou3NTvaybvNgEBo80nOY8GgXvTCeduuA2H1O7dlBKXUNsjXRdkpV3kcSf440PZWDY1B65L8EFPXev9JmYpmwATS/ZCnBHk/tdTYD2GbwwMm3Z3+woSJI0Z3SsLvtFMIIe3wUdQEetNTvGVK8OWm6odepg6kQDZ9CXSxwCc6+ZkzMU1VlZqwfFceB0FggWJ62wtw902Pl5ra+UiQFu7dJypSKoXSc9d5DaK7YKsVCMSmRdpJ3A0p+rhzApmEuqf0MZyH/WGALdmIvIJ4KZIsKbwMtBi0CgYEA/CXGT8lvvD/ep8iFlL188tr8G5m5yUChkKYPgPR9ZJgXr2WA1zE/eBarRJxMokRsxbjNg1fgJvwkB0SBHMVEcP64iG8mW8KUMXBNnVHkfKbNNjQyJHkW1NdliDFXAFvYwHP5TtR2fXlF3Qjjt/08/3VC/65jSr2rlmg6QpA90W0CgYEA6jojCjtOnpq2z1Nxq+FHZGWJnLERvUldWmS7vU9t64R9WoKrl0nf4KzG0Yz0+ARxqaYGFaHaMDeV04qBd+Q/Eq6v3t/qqPfkZ0VAxZx0VJH4RUo6ZkJ3CBovIL5r0nUJcQ7Ty977sWkNWZJwayJ2osRkpS1gMj7Kr7MtlrgUpBMCgYAMRu6T/fPhz55QvxO5/UvzKzRxR5fjM7FqMJu6pKziVAF7Oc7/K1eyyi/GpHGayQgezHO9PG8eltJOREihFP3THqPviLHub9f1XEl7KnyckSaWyA3U9sxkyqQX8IcS7mXsYPk3nGK0k04SiBdGYx8wNQ5xEpWlEv0i0qpbiVhpNQKBgAU4uJFA0zbb8hlbHdycEBBf5xp0JBYZLZiXSEwnGic28+hxo0ujHPeQQnpnbwqcZsBGcDpJOmj6xD9MBijbEMDk3gzU5xFPPWtD8va0zy8XdfZtejHPZ4Hpu4U6WYL+kvBPSU9xMCE7W03U4uawhP6J7Eh2Y3+bQL97+hczEVq7AoGBAKPHvXS6pn9eNvxP3dboYSXEldh8t6iQXTuHfTnbaXOccDK/PqxzvlhCcfKaGLlSFFfURVazlHPWdAGLQS/9Ku1T5CgTWD/Z+DAnO9QA/apHPU2OedR62RRtaHi7Vyd7KJdqId8mIkghACR9LQ3l8LC1pPh/7oyKPbKbqDXiVvUI
923d3561-a381-4267-9610-f026527559d7	9491f4c3-5f17-4a17-9bb1-c0b324265d61	priority	100
f7e64ecf-c082-4ede-901a-5c42c64831bc	22201947-e7bd-43e8-a6cc-eb6dac1b97dd	priority	100
740908fc-186a-4fd9-87f6-61b5935d1f0e	22201947-e7bd-43e8-a6cc-eb6dac1b97dd	secret	C88_ZL-LbS7nTzeaeRpPfg
cbc0e654-d906-4805-82ed-27d2584490eb	22201947-e7bd-43e8-a6cc-eb6dac1b97dd	kid	fbacec14-1571-4f74-b8c2-bb35f7b6b836
75ee30df-81d1-48c7-9608-51a0beb7765d	0e00f6c2-0975-4cc1-861c-78b49d62cb3b	kid	7520f12a-1016-4df0-8738-f90fc43c8dbe
7748302a-9983-4bcf-a0c3-f62746642d08	0e00f6c2-0975-4cc1-861c-78b49d62cb3b	priority	100
690b1877-7e44-4c38-981c-4d81e81cb8c8	0e00f6c2-0975-4cc1-861c-78b49d62cb3b	algorithm	HS512
06223f54-c016-4fef-aa87-0719b64ce171	0e00f6c2-0975-4cc1-861c-78b49d62cb3b	secret	n30-PornkIy2IQ_KFu3FyzYKUrTUsb-3r43OqJVY7xAsoJC92JX3HKOs5P7i0dOpOY9Hg4REQa2fy2eC7Y0LAAGxfKJzmrtSPYixME0YPKtSrQxHObKXL50Tk5hdEBcBDsPxgIt5_sVsFZ8BH6gK2VUlJ_oL3JzkmL0qfAMQVVE
38807d81-26e1-4724-9c63-8181b603ddae	f5479ef5-d439-4ea6-8758-c295619b6a50	priority	100
1001e8dc-bcad-46a2-89e5-7b5b2e0195ff	f5479ef5-d439-4ea6-8758-c295619b6a50	secret	CL4TbEzRwHPZfoauNvRTsQ
7ba3826d-ab38-4b71-b14b-267042426d37	f5479ef5-d439-4ea6-8758-c295619b6a50	kid	c97dc653-c43f-4e1c-a6e2-9052a16d36a7
69abe3b9-6176-4473-9db6-9c3a25dee7ca	d0836b85-4031-4ee0-980f-3a2242dd1e92	kid	7594e99d-ec82-4158-9db8-bc1fd1785ffb
e675fe44-37d9-4c9f-9537-066f066ac927	d0836b85-4031-4ee0-980f-3a2242dd1e92	secret	6WdBZQZCoJfdD8wsl9t3IH-8SumLi9nNK0-4QQ0UHgnVi4pUx-Qmvm4GM6B26hCNIBEBefNvSwnGKWjuihltKUGs4HV05-I4MFqGpK0-gO4xxaVdeukAWU3ZFL92Ys22dsP9UC4QUH4LcJ_3kPLZpGqIVuPVFXZHWiNOyHxnKk8
314c52de-f0aa-49e8-bfb0-559d66285130	d0836b85-4031-4ee0-980f-3a2242dd1e92	priority	100
1a2e3eb1-b5d5-4c25-b0f0-e00c728ffd48	d0836b85-4031-4ee0-980f-3a2242dd1e92	algorithm	HS512
96ba63b6-3503-433f-b832-a79e53c241cf	7af3aa86-e155-4b4e-b62d-5537261645fc	privateKey	MIIEogIBAAKCAQEA9ftyIYHVu2I7xtXRkOkbFlyLHMyVIUVcTIFP2hJRsGND9+gNEL5OuqeNgENFJ0viLVPBf7L+tkXr2hy/1j8h80Qwa+H3RPLgCGNICCutFhS2G5KFaVm3lInLtxR0ZHVugRRwZn09YgmZii8RNxxpOtI/cUFaUiulA780yfBg+VQbpLixF6Bi8ntpiQxz85xcsBjoqREZ0v4+pcTGHR0oCa3f/uvixx53RotdQVYzPY3wVYmFFnusjAPP2AmI6+8p6uq8A9L99b87pIJiFhl0obHJqt0Y77U0cUtfQCxBHAgjaLvgJZq3hn9Sj4k9EuF20mmdCB3sAB/kv+pT2pLDTQIDAQABAoIBACVrtTcnu04gaZucF5+F9tr2BSvCa8V9+cahvXYdZ1QAJezm3mX3rZ5zsvEaSsDpqGTg4wT8RAeSp4is6vo67H1HmF4xKB7THAXqpKCplFFRoUqchbM7NNRiCh21/ObzfnYgl+DfmKD7eOIuut25csDCD2i5WnY5sYbaZbt62CXVu2y0gsAksYnDZXnCsLLhTbtILgain/Ff0NMadCmt+d5queQKZ9swLeRDVSx+XMo3XuLym5CZGBn7c7oBMRBhCUUv/vXIDPR/V5NoZXcsdU6tNhWDvhRTAERPZQoklHCgl/PzUEum1jdw4H6rDREULtJ+QAAkGHtsSBN3jJpDHvkCgYEA/Ai6OU7Du+nCZdfBpLxCTVORDGP2p7uAd0KvHZUZqt1aUSX60I2SuEHNEPY2XWBS6um/nOAcHriJ2x7+tDY7HcMjQc5I/aYf97E1Ju1l28RnEF0729Abz2hJayV+TyxblDXejLM/8hUOlwUo7xoBAFFY1Tzrnfx7yXBUWW3qCOMCgYEA+dpW6eBld8bPu4KokfY8GtmHXVpmBiVCqkwLj3eTpsKFkab3cRYPL1GJn4r4KDii/B4ngysB0Jwt9ZwbPJwtgkOUohDLgpULkbuIKcLRKirSvcCdtKyJ0bfTVzq78yNCI4vua6k5Fhz8d/hjZINl4h/6lTMvnksA6pUT2GGtKg8CgYAJpRvbTZRYAJ4b36HdeAbhhq0qWDj8OkS0S6z/6NDOpSHrsRRkHsbankoGuCVkdWc27jMTAdCRrGY6KHl+1AR3IxyaTBam/lCC3hk2oCchveZjze2Yf+JQkaEdpJWs2fm3NkiKAeIUlZG1XdEvNUOMi5BmuKobpannD5FqyrYGGQKBgHyDRtT+sxJhzZxQnNlAsOtghIkNR9LANtzPUOOKX9a3EgFoB+AfcinOpJsRkmHY98BU9rPzdtATRQQla51k53kHXMb3P7QvurhBkLXfFIXDGYretrkXUwdkaLoL1yZv58H3NjYI+x3+DANtkXrB0MBD14kLHSvxjN9as60d4EIZAoGAEUs/0jFtoQbuViw5Wm1c8YOWmDNog7mxjGXl32Kv+rPWmDahTi9ibajuzPQ7HlsjqnmKcIQSGEVO9yghCkMqnF44IQUF5j59nKHDgIhzl6Qggllw6srazuByiDgjFOag6plXT9E/gUVELInTB/znskHLs6Zh9g1qtmV397esbz0=
6250ebf8-a413-424f-9452-4ec7ce1455c5	7af3aa86-e155-4b4e-b62d-5537261645fc	keyUse	ENC
48bbde69-87b8-4e8f-b15d-c34559d47106	7af3aa86-e155-4b4e-b62d-5537261645fc	priority	100
1f5d9b40-bacb-46fe-a03d-858d3f06893d	7af3aa86-e155-4b4e-b62d-5537261645fc	certificate	MIICpTCCAY0CBgGc1Q6uhTANBgkqhkiG9w0BAQsFADAWMRQwEgYDVQQDDAtxdWludGEteXB1YTAeFw0yNjAzMTAwMDAyMTVaFw0zNjAzMTAwMDAzNTVaMBYxFDASBgNVBAMMC3F1aW50YS15cHVhMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9ftyIYHVu2I7xtXRkOkbFlyLHMyVIUVcTIFP2hJRsGND9+gNEL5OuqeNgENFJ0viLVPBf7L+tkXr2hy/1j8h80Qwa+H3RPLgCGNICCutFhS2G5KFaVm3lInLtxR0ZHVugRRwZn09YgmZii8RNxxpOtI/cUFaUiulA780yfBg+VQbpLixF6Bi8ntpiQxz85xcsBjoqREZ0v4+pcTGHR0oCa3f/uvixx53RotdQVYzPY3wVYmFFnusjAPP2AmI6+8p6uq8A9L99b87pIJiFhl0obHJqt0Y77U0cUtfQCxBHAgjaLvgJZq3hn9Sj4k9EuF20mmdCB3sAB/kv+pT2pLDTQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQDOFoQ3gX657vgg3DzGJ4f1mXNPVLUx0mFsFCQgXyK5mla+mp+X6/PTmAh6eZO/KGsrF9IHfS60sX3fO1pCqSsGQsul69ysGWXXkcKzyef3A6TR1ANkqhJnrzHCu38CJQfLPyn84MzpQSKrwxw9w6UgM6mt+YZ+fWKUABJ8T2xXmXJ/JvgCYSI7jSyQKZT6lIe1nGPr6yS8aHyB5yphqWpmyYLaDVTNNbyiJMIreMh0AICaRSGvIO+FPSZozaa/cnF17nvzLf1DsarDOKPJBu8mXnSuHarhOkvFbnx4LbnAAbAByO8bI39xiEaWm3C1D2YdkKvpI+S038eQjEq9W6tc
afe9a582-476d-4f94-9bf0-9df624198489	7af3aa86-e155-4b4e-b62d-5537261645fc	algorithm	RSA-OAEP
542f42a3-356b-4754-bf96-e7a375c13452	e6f3fce2-d8d0-49ac-8b77-b81302539072	certificate	MIICpTCCAY0CBgGc1Q6uPjANBgkqhkiG9w0BAQsFADAWMRQwEgYDVQQDDAtxdWludGEteXB1YTAeFw0yNjAzMTAwMDAyMTVaFw0zNjAzMTAwMDAzNTVaMBYxFDASBgNVBAMMC3F1aW50YS15cHVhMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp+hlaW4PFh3vHj9XCO/GASEI+q+68vYwbXsJzSdyWWfCvp45rgp+1EhxAu8blDDsoPnJmjbBhrFzVZGYTerEY49hBLKE204txl7PHeteq4/9PeZ2Y2Mgog0ARITjireZ07TFSfB+fDTpvUwbltYalrlR4RMHNOy/oyOE2EjekdqizBee7iJcWZ/EEK/5T9JLpOvmthyWmTu5p0Ig+KZVr232j4C6HFKxkOobhKvVOzZz6Bk7c1m0EIR/p3F9FS2BIgdJyLGdZwrCqrcg9CIV2IxLt0OU/e545vPhJMZsiaM+zzyPBVADVGANJ57/g2J9rlcE4W2W8tX+4viTB4bDEwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAFX6/bF5CHdVJi4IRVyAV7j2rL5ZuC9caaUkBmIrXTYFhhSW+bt6BMHPBHFAPO03baXyDStX8bNqfcBTuTZap6AG4XksQPv47gi1qshOn5aVzryIqvrLMKFf8ssSYWplCTTVdUIuMDornQKmCszWWY2DH8tmE2Jite7tEUILlHsc9fMrrsZjbn/zfhqAOBKuFFjN6sjivSxjDO3HB9VhegoTuSzQO7pqpawvYaARdFRDUgFlrgKTFEf9FVuUAF+oHWkFD1aGHjAohdMH8ccSeXIYFuYehunUj8H7ovXQbAO3A9sag2z4C870zeljLB7whwktMj+PtF9BXn00GzR6aY
9bd824d6-5989-4f72-9743-5797f9103729	e6f3fce2-d8d0-49ac-8b77-b81302539072	privateKey	MIIEowIBAAKCAQEAp+hlaW4PFh3vHj9XCO/GASEI+q+68vYwbXsJzSdyWWfCvp45rgp+1EhxAu8blDDsoPnJmjbBhrFzVZGYTerEY49hBLKE204txl7PHeteq4/9PeZ2Y2Mgog0ARITjireZ07TFSfB+fDTpvUwbltYalrlR4RMHNOy/oyOE2EjekdqizBee7iJcWZ/EEK/5T9JLpOvmthyWmTu5p0Ig+KZVr232j4C6HFKxkOobhKvVOzZz6Bk7c1m0EIR/p3F9FS2BIgdJyLGdZwrCqrcg9CIV2IxLt0OU/e545vPhJMZsiaM+zzyPBVADVGANJ57/g2J9rlcE4W2W8tX+4viTB4bDEwIDAQABAoIBAACxhlmydAAqDNFvKYjb6fwZ4Cjzji4as4XVkEDMp5LpnsryvwI9ANvCQNU7mj2OcNZpXFp1XMSVpF2iGvhCE/WP+LKa73mIZSAAnD0KD9Cy6N+B6CiY7DWUdX/S8gL3UdH8rnk+XlyySz+A5PbxLhhD7lajoXqnz6gjfr+gmBVkyWN9AiQEMYOhYhbvpnpNQz97zCmX+BPHDuNR6Qq2sD6n4aks/pjpuAMdvrGnmTKDnC+9QauOOIvbgk6GWgU8IwHntt+2euxc1NFvM1/yOKCcX3GEh+jxpmqJpIFsdlkwNlY7fuTdO2jD44RJ9UwXVen+t6ZXGbicS0THwQw5dcECgYEA0/AcbSZ3JqYcTogMxCepqBLEtzg+Q5oCZ487UTblHP0rAXWLPJGrhaQOyRhkU4oS7aJ+zpW0ms41I+91bBiwx1KxYL7o5raVAx+wpT3qBQ6JyzrtNz4hWKo4MqbnnJV89zwfDfPljbiMn7UZfa0T27T9WNFoQu6yeL1LqPZAJQsCgYEAytDi9EdaUFnCJciRee5k91uRc4kYA8nQptwAKfb5scADmpCC8keEP1AU4X5J2n3uhuMijOLK1beJ1+dIegwaIc9IkAsGiR+q26qI3uIbx31mE4Fn7qPX0d9be6udc5gw0T4mhwh4Ep3APp6Gh3cgmSYm7ykp13E1Sx8IgWr2jxkCgYEAuXCLftm8QZ3wgKo7+Vn28W/SnbnhiQb1eaVysmIE4HOIMJOny8xtttZQdZYZGptianjW2W1WBGnPR20bYgTMkOsC9vK0GELDaWNODZd57wjEeoVJ0B9V0B3IZFFzsKeAG1eImI3CWlmS+Z7c+gD5sjDPx+oafw4aLNj8nY8xmvcCgYAf/vI+/V63HGHvmgOxS7Wr/EsnAfLC7jy3hi+Ubz0XGKa45KFz3Wp0noqcxFKcYI+Xwg96UHEVZD4fzIH3lx8sREo7RzIBwfgvbWjoWJ1vTE4lN54Q3NI4btc83GcbjF/vMrHbTa+7qB1jA5lPl7CR2J39zu2l6lsw/bVWzWwWSQKBgH8/Gy3OsEwCIcvfCCh4C0sOB46Bd4of9jQYmycmFLQhG8f6JUfQxYdrrlC6FKrksBuBWl3lrCnOW3EAwlhOEOwYwd8ZLXbqgI14FmOox5WYCqarqtcfobM2lP4QkB0fh4kgTEPS36NM/IpVztqVypdQ+RAEYDC9kxhhvcrub4db
f743fc43-b77f-46e8-9af1-e92678382bfa	74c5892d-172e-4a13-ac88-1b0072809699	host-sending-registration-request-must-match	true
06149199-1a23-4715-a92e-b60e95d1baf8	74c5892d-172e-4a13-ac88-1b0072809699	client-uris-must-match	true
1a15d5ed-0928-416b-bbc5-6605aea4880a	9e6684b9-e5e4-4a23-a81f-7d65f41b01a5	allow-default-scopes	true
a155dc17-a14e-4f0c-a777-0aae48809aa1	7f11e88d-3cb9-43fa-a555-c831b8e5595b	allow-default-scopes	true
2d3dfdb9-687b-4e8d-a612-cfda4c76b009	b353e6eb-d688-4800-bfde-d415a352f130	max-clients	200
512ea2b6-cf37-486a-b4c5-674acbc6a70c	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
fec93159-ebbd-42c5-b75e-efc92ac46004	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	saml-role-list-mapper
014c5acb-3d7d-47d6-8167-bd91faeeff44	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	oidc-full-name-mapper
2e513af4-7468-4984-b5e0-fb16dfc2b398	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
ddb067d7-494b-4856-abd6-1cd919225348	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	saml-user-attribute-mapper
14d27fe8-4993-47ce-86b2-919804677678	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
4703b47d-eab6-41bd-9501-87c7f9e6f44f	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	saml-user-property-mapper
496dcbbc-0b5a-42c2-b611-07d54eb444db	6b61d3d2-e33f-4f6f-91d2-661092d97431	allowed-protocol-mapper-types	oidc-address-mapper
b6f28369-cd59-461b-a135-114725064e55	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
8202d148-8d8b-4524-b39d-9c47f201e406	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	oidc-full-name-mapper
17fdea60-aa45-4b10-9156-125a5baec158	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	saml-user-property-mapper
a97c27cb-76b7-4a0e-a983-9bcbcb888ab2	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
d269c146-6cc6-4d3e-ad9c-7bac7237d5e7	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
fc2af1fd-7a64-4ec2-a8b4-f2a7b438647a	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	saml-user-attribute-mapper
a1e075e9-ab6e-495b-98a3-b00d64c3ad53	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	saml-role-list-mapper
cca4b5e2-a9dc-4561-9280-b1a5c9a5d0f5	a086671a-068f-4e10-a1dd-0d7e3b222e4c	allowed-protocol-mapper-types	oidc-address-mapper
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.composite_role (composite, child_role) FROM stdin;
094ca4aa-a59e-4463-b492-b7762d5950c3	549663cf-af1d-4176-8b27-7c8c26e1083f
094ca4aa-a59e-4463-b492-b7762d5950c3	3e95950d-949b-448b-8348-28b1afedc383
094ca4aa-a59e-4463-b492-b7762d5950c3	073fa9d6-8a69-4a3b-aede-107ee59e9df4
094ca4aa-a59e-4463-b492-b7762d5950c3	30429526-955a-4dd7-a60d-2d1fdc4348c0
094ca4aa-a59e-4463-b492-b7762d5950c3	476f4b98-4be1-4457-8306-9e0d6148f666
094ca4aa-a59e-4463-b492-b7762d5950c3	4ae7184c-3341-42cd-a265-0fd28ce5d529
094ca4aa-a59e-4463-b492-b7762d5950c3	8c0eb1a0-53bb-4cd0-97b2-adf578e601f4
094ca4aa-a59e-4463-b492-b7762d5950c3	b500f8ab-1382-47e3-b4c2-2805d15246a4
094ca4aa-a59e-4463-b492-b7762d5950c3	f3b5540b-db08-4268-ad8a-8801a11db79d
094ca4aa-a59e-4463-b492-b7762d5950c3	3abeddab-8013-41f3-9755-0fe3f41da551
094ca4aa-a59e-4463-b492-b7762d5950c3	ff3d2c28-3f6e-4ab3-9311-3c5a5dca16f0
094ca4aa-a59e-4463-b492-b7762d5950c3	5648fb7c-f0a8-479b-9d66-234affa29e63
094ca4aa-a59e-4463-b492-b7762d5950c3	82539967-8892-455c-9568-ee4310c734bf
094ca4aa-a59e-4463-b492-b7762d5950c3	df7f88cf-c4c9-4b77-bb45-204cd05eb173
094ca4aa-a59e-4463-b492-b7762d5950c3	95a36a78-8686-4528-a66e-9d0fac949f09
094ca4aa-a59e-4463-b492-b7762d5950c3	374fcf09-a28e-46fe-83fd-c39da332212a
094ca4aa-a59e-4463-b492-b7762d5950c3	609716fd-5ee6-4075-bb03-e9ff43c15ac7
094ca4aa-a59e-4463-b492-b7762d5950c3	554ece2a-9743-40fa-9f1a-c021a155e946
30429526-955a-4dd7-a60d-2d1fdc4348c0	554ece2a-9743-40fa-9f1a-c021a155e946
30429526-955a-4dd7-a60d-2d1fdc4348c0	95a36a78-8686-4528-a66e-9d0fac949f09
476f4b98-4be1-4457-8306-9e0d6148f666	374fcf09-a28e-46fe-83fd-c39da332212a
776b2711-9757-468e-ba12-bd367256a8c8	49a7a532-2417-46b1-bef2-47042cce81ed
776b2711-9757-468e-ba12-bd367256a8c8	624216f9-9ba9-4813-bb4f-2fdd008b5645
624216f9-9ba9-4813-bb4f-2fdd008b5645	1ab458ee-e7d8-4871-a5e0-a04cd5ea883d
99fa23b7-e3be-49aa-8773-01ba5451d42f	bafbf1e0-6924-4625-907d-61bc2dc0fffa
094ca4aa-a59e-4463-b492-b7762d5950c3	943144ac-34b5-4715-9d66-8a3da931b15a
776b2711-9757-468e-ba12-bd367256a8c8	06fe37f5-4b92-45e5-80ad-498929412bfb
776b2711-9757-468e-ba12-bd367256a8c8	a7f248b9-9ca5-4e78-8a17-9fb9f2dfe98e
094ca4aa-a59e-4463-b492-b7762d5950c3	6e389bb3-ce7c-4692-b5b2-07b3c7dc8f12
094ca4aa-a59e-4463-b492-b7762d5950c3	f6753958-1377-446b-86f9-36ac4f21a0b8
094ca4aa-a59e-4463-b492-b7762d5950c3	31d48bd4-6bd9-48fc-ad9b-80039775b22c
094ca4aa-a59e-4463-b492-b7762d5950c3	69dff334-6f22-4793-a201-f0ed5d8132be
094ca4aa-a59e-4463-b492-b7762d5950c3	e5b48493-d112-4aef-9e81-33489c01c97f
094ca4aa-a59e-4463-b492-b7762d5950c3	81e028c1-02df-410a-a4fd-616c5edbcd09
094ca4aa-a59e-4463-b492-b7762d5950c3	76cb6387-3e91-431a-a856-b6fb4e4701bd
094ca4aa-a59e-4463-b492-b7762d5950c3	b1326db7-413f-4021-9413-78c1c6a0d5d0
094ca4aa-a59e-4463-b492-b7762d5950c3	4447ed1d-d58d-4ed9-ad86-6f71cc921da5
094ca4aa-a59e-4463-b492-b7762d5950c3	09d67c9e-d80b-418e-99ed-d62d074e86cc
094ca4aa-a59e-4463-b492-b7762d5950c3	e1bcc396-4bc3-4326-a75a-edc59c25778d
094ca4aa-a59e-4463-b492-b7762d5950c3	3723b752-9dc2-4172-afb3-1135dffb945c
094ca4aa-a59e-4463-b492-b7762d5950c3	8f18532c-0900-413b-bed3-21719a56cabb
094ca4aa-a59e-4463-b492-b7762d5950c3	627cc7b2-f228-4eb2-8893-e2e445d3bb14
094ca4aa-a59e-4463-b492-b7762d5950c3	0ef24ca6-2eb2-42ff-bc6e-1effdf3d37bc
094ca4aa-a59e-4463-b492-b7762d5950c3	6c8a40bb-6f5d-4167-a4dd-3fdfaee65203
094ca4aa-a59e-4463-b492-b7762d5950c3	9eb455d4-30d1-40d0-acec-1fe09fc4bdfb
31d48bd4-6bd9-48fc-ad9b-80039775b22c	627cc7b2-f228-4eb2-8893-e2e445d3bb14
31d48bd4-6bd9-48fc-ad9b-80039775b22c	9eb455d4-30d1-40d0-acec-1fe09fc4bdfb
69dff334-6f22-4793-a201-f0ed5d8132be	0ef24ca6-2eb2-42ff-bc6e-1effdf3d37bc
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	2dfb0f70-a2c3-46d1-9a53-9c5d00138a92
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	b1bf9883-5115-4bdb-8dfe-ae85841fbbd1
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	5b37f676-c63d-4f38-bd8f-c46a0b9e9577
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	bfc43dc6-920b-46c1-a1c1-a11f5be1022d
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	aee34c51-3a24-495d-aabb-09d829552ede
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	ac694953-e165-443e-8716-06860a9a7b24
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	f03e60ff-d5e0-4a45-a077-c5ffaa6acaf9
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	334b38e6-7e76-4529-babc-4f5b9d070041
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	89ff538a-8fcd-4a99-a186-0fd5218c1df2
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	bd2b7a75-fdcd-4ebb-9976-a684f92b6445
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	8f9f1a36-6669-4e38-b6b3-3a63756dc855
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	45c183f9-cddb-4ffc-83f6-ebfbef307480
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	d8079922-fd5b-4872-8c09-1d0fe58e5c56
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	8b61eb95-be8d-4bfd-95e1-4600c0978efd
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	5b8650bf-8ad3-4bc4-b6e6-e499e0f394ef
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	2aef1606-6d21-4edb-a516-7da893bbea9d
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	1ac05c5c-2b14-4df0-ba34-cae4148f86c8
40ba6aab-743b-4a42-88be-64c606013207	1c427514-2ed9-484f-862f-1eee7968e512
5b37f676-c63d-4f38-bd8f-c46a0b9e9577	8b61eb95-be8d-4bfd-95e1-4600c0978efd
5b37f676-c63d-4f38-bd8f-c46a0b9e9577	1ac05c5c-2b14-4df0-ba34-cae4148f86c8
bfc43dc6-920b-46c1-a1c1-a11f5be1022d	5b8650bf-8ad3-4bc4-b6e6-e499e0f394ef
40ba6aab-743b-4a42-88be-64c606013207	78c70b68-43b3-41b9-bb50-1631aa1a3cf2
78c70b68-43b3-41b9-bb50-1631aa1a3cf2	18e65ae0-68e4-441a-9858-453bd98af48c
f8eec290-372a-4c51-bb3e-977e144384f6	45430fde-c450-4a8f-91d6-2321b78548c3
094ca4aa-a59e-4463-b492-b7762d5950c3	7bb41f93-f2fc-4460-aea4-f81e8b8d248f
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	0cd820a1-99f7-4803-b108-c65c31283370
40ba6aab-743b-4a42-88be-64c606013207	cf64d75b-bb8f-4540-b035-bd0b1442c137
40ba6aab-743b-4a42-88be-64c606013207	363b87c8-67d6-44ed-ab88-df4617d12ae1
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
9a5e8913-46fc-493e-ba52-474224dd8e8d	\N	password	03d9df00-e0f0-4252-92f3-fbe863910716	1773101140835	My password	{"value":"b3rpzC3VQyGarzfYu6oAjhQGm1EUC1y3Q+LVsNU+sa4=","salt":"EtdR2DArmsXW6YzP3bhQvQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
7218016d-2afc-46d5-a3a7-f03b4fce4f4e	\N	password	5727be85-b2de-4841-9e4f-4381021fa33e	1778451754815	My password	{"value":"NcJuKwqfJ2b9PGyQ2m62lSFpnLxpIkqNxKRbBRuInRk=","salt":"yuX7iuI4cjq4vJJ6I8Z/MQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
ba7c8541-69f4-4b3c-9e99-46701adf83ca	\N	password	38551cec-aa01-4483-a9e3-18bb0ca362cc	1778458314148	My password	{"value":"u7j4598q5MSJwWgTNvyQNa9oRGieg/n6GFyd/mJxZvM=","salt":"RpGC55h2jtf9PsD1E46nhQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2026-03-09 23:06:06.582023	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	3097566368
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2026-03-09 23:06:06.596338	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	3097566368
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2026-03-09 23:06:06.618637	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.29.1	\N	\N	3097566368
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2026-03-09 23:06:06.621439	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	3097566368
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2026-03-09 23:06:06.68832	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	3097566368
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2026-03-09 23:06:06.692047	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	3097566368
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2026-03-09 23:06:06.733265	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	3097566368
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2026-03-09 23:06:06.738502	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	3097566368
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2026-03-09 23:06:06.742223	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.29.1	\N	\N	3097566368
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2026-03-09 23:06:06.796152	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.29.1	\N	\N	3097566368
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2026-03-09 23:06:06.820922	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	3097566368
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2026-03-09 23:06:06.823986	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	3097566368
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2026-03-09 23:06:06.831644	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	3097566368
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2026-03-09 23:06:06.840422	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.29.1	\N	\N	3097566368
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2026-03-09 23:06:06.841651	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2026-03-09 23:06:06.843317	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.29.1	\N	\N	3097566368
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2026-03-09 23:06:06.844974	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.29.1	\N	\N	3097566368
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2026-03-09 23:06:06.862516	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.29.1	\N	\N	3097566368
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2026-03-09 23:06:06.878126	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	3097566368
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2026-03-09 23:06:06.881041	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	3097566368
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2026-03-09 23:06:06.882888	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	3097566368
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2026-03-09 23:06:06.884688	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	3097566368
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2026-03-09 23:06:06.93465	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.29.1	\N	\N	3097566368
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2026-03-09 23:06:06.93914	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	3097566368
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2026-03-09 23:06:06.940631	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	3097566368
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2026-03-09 23:06:07.139754	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.29.1	\N	\N	3097566368
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2026-03-09 23:06:07.17781	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.29.1	\N	\N	3097566368
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2026-03-09 23:06:07.181551	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	3097566368
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2026-03-09 23:06:07.229557	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.29.1	\N	\N	3097566368
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2026-03-09 23:06:07.243889	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.29.1	\N	\N	3097566368
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2026-03-09 23:06:07.25934	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.29.1	\N	\N	3097566368
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2026-03-09 23:06:07.261864	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.29.1	\N	\N	3097566368
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2026-03-09 23:06:07.264699	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2026-03-09 23:06:07.266078	34	MARK_RAN	9:3a32bace77c84d7678d035a7f5a8084e	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	3097566368
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2026-03-09 23:06:07.277243	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	3097566368
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2026-03-09 23:06:07.280695	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.29.1	\N	\N	3097566368
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2026-03-09 23:06:07.284045	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	3097566368
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2026-03-09 23:06:07.286639	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.29.1	\N	\N	3097566368
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2026-03-09 23:06:07.289926	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.29.1	\N	\N	3097566368
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2026-03-09 23:06:07.291357	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	3097566368
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2026-03-09 23:06:07.292883	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	3097566368
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2026-03-09 23:06:07.295492	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.29.1	\N	\N	3097566368
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2026-03-09 23:06:08.124552	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.29.1	\N	\N	3097566368
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2026-03-09 23:06:08.127157	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.29.1	\N	\N	3097566368
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2026-03-09 23:06:08.130079	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	3097566368
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2026-03-09 23:06:08.132224	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.29.1	\N	\N	3097566368
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2026-03-09 23:06:08.133235	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	3097566368
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2026-03-09 23:06:08.1938	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.29.1	\N	\N	3097566368
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2026-03-09 23:06:08.196359	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.29.1	\N	\N	3097566368
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2026-03-09 23:06:08.221404	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.29.1	\N	\N	3097566368
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2026-03-09 23:06:08.463498	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.29.1	\N	\N	3097566368
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2026-03-09 23:06:08.466017	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2026-03-09 23:06:08.467575	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.29.1	\N	\N	3097566368
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2026-03-09 23:06:08.468922	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.29.1	\N	\N	3097566368
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2026-03-09 23:06:08.47348	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.29.1	\N	\N	3097566368
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2026-03-09 23:06:08.475988	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.29.1	\N	\N	3097566368
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2026-03-09 23:06:08.497388	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.29.1	\N	\N	3097566368
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2026-03-09 23:06:08.695767	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.29.1	\N	\N	3097566368
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2026-03-09 23:06:08.706084	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.29.1	\N	\N	3097566368
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2026-03-09 23:06:08.709144	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	3097566368
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2026-03-09 23:06:08.712921	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.29.1	\N	\N	3097566368
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2026-03-09 23:06:08.71589	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.29.1	\N	\N	3097566368
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2026-03-09 23:06:08.718016	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	3097566368
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2026-03-09 23:06:08.719521	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	3097566368
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2026-03-09 23:06:08.721019	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.29.1	\N	\N	3097566368
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2026-03-09 23:06:08.7399	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.29.1	\N	\N	3097566368
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2026-03-09 23:06:08.756978	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.29.1	\N	\N	3097566368
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2026-03-09 23:06:08.759674	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.29.1	\N	\N	3097566368
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2026-03-09 23:06:08.77794	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.29.1	\N	\N	3097566368
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2026-03-09 23:06:08.781807	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.29.1	\N	\N	3097566368
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2026-03-09 23:06:08.784959	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	3097566368
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2026-03-09 23:06:08.789973	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	3097566368
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2026-03-09 23:06:08.794606	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	3097566368
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2026-03-09 23:06:08.79607	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	3097566368
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2026-03-09 23:06:08.805821	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.29.1	\N	\N	3097566368
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2026-03-09 23:06:08.823388	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	3097566368
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2026-03-09 23:06:08.82583	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.29.1	\N	\N	3097566368
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2026-03-09 23:06:08.826803	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.29.1	\N	\N	3097566368
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2026-03-09 23:06:08.83474	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.29.1	\N	\N	3097566368
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2026-03-09 23:06:08.836354	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.29.1	\N	\N	3097566368
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2026-03-09 23:06:08.853917	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.29.1	\N	\N	3097566368
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2026-03-09 23:06:08.855428	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	3097566368
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2026-03-09 23:06:08.859157	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	3097566368
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2026-03-09 23:06:08.860706	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	3097566368
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2026-03-09 23:06:08.878413	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	3097566368
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2026-03-09 23:06:08.881002	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.29.1	\N	\N	3097566368
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2026-03-09 23:06:08.884013	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.29.1	\N	\N	3097566368
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2026-03-09 23:06:08.888758	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.29.1	\N	\N	3097566368
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.891858	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.29.1	\N	\N	3097566368
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.895033	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.29.1	\N	\N	3097566368
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.912083	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.915312	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.29.1	\N	\N	3097566368
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.916327	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	3097566368
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.919947	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	3097566368
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.921166	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.29.1	\N	\N	3097566368
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2026-03-09 23:06:08.924625	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.29.1	\N	\N	3097566368
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:08.966917	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:08.968036	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:08.973177	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:08.992116	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:08.993455	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:09.011918	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.29.1	\N	\N	3097566368
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2026-03-09 23:06:09.014413	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.29.1	\N	\N	3097566368
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2026-03-09 23:06:09.017789	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.29.1	\N	\N	3097566368
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2026-03-09 23:06:09.038761	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.29.1	\N	\N	3097566368
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2026-03-09 23:06:09.057371	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	3097566368
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2026-03-09 23:06:09.080516	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.29.1	\N	\N	3097566368
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2026-03-09 23:06:09.083503	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.29.1	\N	\N	3097566368
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2026-03-09 23:06:09.101477	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	3097566368
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2026-03-09 23:06:09.103162	110	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	3097566368
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2026-03-09 23:06:09.108274	111	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2026-03-09 23:06:09.110685	112	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.29.1	\N	\N	3097566368
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2026-03-09 23:06:09.122054	113	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	3097566368
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2026-03-09 23:06:09.123954	114	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.29.1	\N	\N	3097566368
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2026-03-09 23:06:09.126352	115	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.29.1	\N	\N	3097566368
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2026-03-09 23:06:09.127268	116	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.29.1	\N	\N	3097566368
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2026-03-09 23:06:09.130007	117	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.29.1	\N	\N	3097566368
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2026-03-09 23:06:09.131984	118	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	3097566368
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2026-03-09 23:06:09.199586	119	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.29.1	\N	\N	3097566368
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2026-03-09 23:06:09.201718	120	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.29.1	\N	\N	3097566368
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2026-03-09 23:06:09.203992	121	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2026-03-09 23:06:09.225502	122	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2026-03-09 23:06:09.228956	123	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.29.1	\N	\N	3097566368
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2026-03-09 23:06:09.230525	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2026-03-09 23:06:09.232677	125	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.240109	126	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.277621	127	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-cleanup-uss-createdon	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.311135	128	EXECUTED	9:78ab4fc129ed5e8265dbcc3485fba92f	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-cleanup-uss-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.32831	129	EXECUTED	9:de5f7c1f7e10994ed8b62e621d20eaab	dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-cleanup-uss-by-usersess	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.346607	130	EXECUTED	9:6eee220d024e38e89c799417ec33667f	dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-cleanup-css-preload	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.362665	131	EXECUTED	9:5411d2fb2891d3e8d63ddb55dfa3c0c9	dropIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.36393	132	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.384036	133	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	3097566368
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.393343	134	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	3097566368
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.401208	135	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	3097566368
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.402763	136	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	3097566368
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2026-03-09 23:06:09.453166	137	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.29.1	\N	\N	3097566368
26.0.0-org-alias	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.458394	138	EXECUTED	9:6ef7d63e4412b3c2d66ed179159886a4	addColumn tableName=ORG; update tableName=ORG; addNotNullConstraint columnName=ALIAS, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_ALIAS, tableName=ORG		\N	4.29.1	\N	\N	3097566368
26.0.0-org-group	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.464404	139	EXECUTED	9:da8e8087d80ef2ace4f89d8c5b9ca223	addColumn tableName=KEYCLOAK_GROUP; update tableName=KEYCLOAK_GROUP; addNotNullConstraint columnName=TYPE, tableName=KEYCLOAK_GROUP; customChange		\N	4.29.1	\N	\N	3097566368
26.0.0-org-indexes	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.488955	140	EXECUTED	9:79b05dcd610a8c7f25ec05135eec0857	createIndex indexName=IDX_ORG_DOMAIN_ORG_ID, tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	3097566368
26.0.0-org-group-membership	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.492693	141	EXECUTED	9:a6ace2ce583a421d89b01ba2a28dc2d4	addColumn tableName=USER_GROUP_MEMBERSHIP; update tableName=USER_GROUP_MEMBERSHIP; addNotNullConstraint columnName=MEMBERSHIP_TYPE, tableName=USER_GROUP_MEMBERSHIP		\N	4.29.1	\N	\N	3097566368
31296-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.497584	142	EXECUTED	9:64ef94489d42a358e8304b0e245f0ed4	createTable tableName=REVOKED_TOKEN; addPrimaryKey constraintName=CONSTRAINT_RT, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	3097566368
31725-index-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.517872	143	EXECUTED	9:b994246ec2bf7c94da881e1d28782c7b	createIndex indexName=IDX_REV_TOKEN_ON_EXPIRE, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	3097566368
26.0.0-idps-for-login	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.558542	144	EXECUTED	9:51f5fffadf986983d4bd59582c6c1604	addColumn tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_REALM_ORG, tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_FOR_LOGIN, tableName=IDENTITY_PROVIDER; customChange		\N	4.29.1	\N	\N	3097566368
26.0.0-32583-drop-redundant-index-on-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.572333	145	EXECUTED	9:24972d83bf27317a055d234187bb4af9	dropIndex indexName=IDX_US_SESS_ID_ON_CL_SESS, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	3097566368
26.0.0.32582-remove-tables-user-session-user-session-note-and-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.578794	146	EXECUTED	9:febdc0f47f2ed241c59e60f58c3ceea5	dropTable tableName=CLIENT_SESSION_ROLE; dropTable tableName=CLIENT_SESSION_NOTE; dropTable tableName=CLIENT_SESSION_PROT_MAPPER; dropTable tableName=CLIENT_SESSION_AUTH_STATUS; dropTable tableName=CLIENT_USER_SESSION_NOTE; dropTable tableName=CLI...		\N	4.29.1	\N	\N	3097566368
26.0.0-33201-org-redirect-url	keycloak	META-INF/jpa-changelog-26.0.0.xml	2026-03-09 23:06:09.581406	147	EXECUTED	9:4d0e22b0ac68ebe9794fa9cb752ea660	addColumn tableName=ORG		\N	4.29.1	\N	\N	3097566368
29399-jdbc-ping-default	keycloak	META-INF/jpa-changelog-26.1.0.xml	2026-03-09 23:06:09.586201	148	EXECUTED	9:007dbe99d7203fca403b89d4edfdf21e	createTable tableName=JGROUPS_PING; addPrimaryKey constraintName=CONSTRAINT_JGROUPS_PING, tableName=JGROUPS_PING		\N	4.29.1	\N	\N	3097566368
26.1.0-34013	keycloak	META-INF/jpa-changelog-26.1.0.xml	2026-03-09 23:06:09.590622	149	EXECUTED	9:e6b686a15759aef99a6d758a5c4c6a26	addColumn tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	3097566368
26.1.0-34380	keycloak	META-INF/jpa-changelog-26.1.0.xml	2026-03-09 23:06:09.593931	150	EXECUTED	9:ac8b9edb7c2b6c17a1c7a11fcf5ccf01	dropTable tableName=USERNAME_LOGIN_FAILURE		\N	4.29.1	\N	\N	3097566368
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
973d96fb-e7bb-493a-b27f-0020b0da1731	e37042e7-33a2-4de2-bdd2-ec9f9c739814	f
973d96fb-e7bb-493a-b27f-0020b0da1731	62c0f6b5-5e6f-437b-9879-862536176c30	t
973d96fb-e7bb-493a-b27f-0020b0da1731	133e76b9-1121-4c1d-aed9-24bf3a8ca799	t
973d96fb-e7bb-493a-b27f-0020b0da1731	1ee33799-b762-4af3-a32e-102ca6062249	t
973d96fb-e7bb-493a-b27f-0020b0da1731	4e5546b8-2e8d-435d-a164-19978835c118	t
973d96fb-e7bb-493a-b27f-0020b0da1731	0579c7ef-131c-44dd-9879-6d9fd5605507	f
973d96fb-e7bb-493a-b27f-0020b0da1731	405408a2-31c1-4e69-ae4d-edb75d4d335f	f
973d96fb-e7bb-493a-b27f-0020b0da1731	b1c35655-b9d3-426a-8b84-578aa7df9550	t
973d96fb-e7bb-493a-b27f-0020b0da1731	5fa39419-de3c-4f95-8889-70143742d2ce	t
973d96fb-e7bb-493a-b27f-0020b0da1731	a9da574d-4857-4a68-93b8-1235e15fcccc	f
973d96fb-e7bb-493a-b27f-0020b0da1731	3ad51afb-b9b9-4451-9a1c-54a80c6678ba	t
973d96fb-e7bb-493a-b27f-0020b0da1731	bab57543-7701-45d5-8ae3-82024fa8dd5a	t
973d96fb-e7bb-493a-b27f-0020b0da1731	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74	f
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	afebdb66-b1d8-4c2f-bfac-3ef87c1c4664	f
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	070dcf44-8b37-4d44-ba36-180871706c7b	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	69508c30-cabe-4cd8-b8ea-d1067db97f42	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	dc4f3172-7fa8-4a40-9f96-871d134212ce	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f05f9faf-6a40-4228-8afd-3bb9eadefa9a	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	34005ce2-fcdb-45e3-a007-d3fb35796dfd	f
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	38635cee-3db8-49e7-bb41-37b4b70ffefd	f
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	cf806293-5721-4f38-9044-726dd7b53b62	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	73fbb70d-f9b2-4d22-bd90-91a1c580202f	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	73475f69-7535-4746-b4c6-af4db747fb8a	f
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	4c0c22e0-1a30-4ccb-8a60-8552041ab07a	t
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	20658153-e9d8-4fdd-bdee-764461e3446e	f
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only, organization_id, hide_on_login) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: jgroups_ping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.jgroups_ping (address, name, cluster_name, ip, coord) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.keycloak_group (id, name, parent_group, realm_id, type) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
776b2711-9757-468e-ba12-bd367256a8c8	973d96fb-e7bb-493a-b27f-0020b0da1731	f	${role_default-roles}	default-roles-master	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	\N
094ca4aa-a59e-4463-b492-b7762d5950c3	973d96fb-e7bb-493a-b27f-0020b0da1731	f	${role_admin}	admin	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	\N
549663cf-af1d-4176-8b27-7c8c26e1083f	973d96fb-e7bb-493a-b27f-0020b0da1731	f	${role_create-realm}	create-realm	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	\N
3e95950d-949b-448b-8348-28b1afedc383	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_create-client}	create-client	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
073fa9d6-8a69-4a3b-aede-107ee59e9df4	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-realm}	view-realm	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
30429526-955a-4dd7-a60d-2d1fdc4348c0	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-users}	view-users	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
476f4b98-4be1-4457-8306-9e0d6148f666	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-clients}	view-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
4ae7184c-3341-42cd-a265-0fd28ce5d529	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-events}	view-events	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
8c0eb1a0-53bb-4cd0-97b2-adf578e601f4	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-identity-providers}	view-identity-providers	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
b500f8ab-1382-47e3-b4c2-2805d15246a4	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_view-authorization}	view-authorization	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
f3b5540b-db08-4268-ad8a-8801a11db79d	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-realm}	manage-realm	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
3abeddab-8013-41f3-9755-0fe3f41da551	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-users}	manage-users	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
ff3d2c28-3f6e-4ab3-9311-3c5a5dca16f0	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-clients}	manage-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
5648fb7c-f0a8-479b-9d66-234affa29e63	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-events}	manage-events	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
82539967-8892-455c-9568-ee4310c734bf	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-identity-providers}	manage-identity-providers	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
df7f88cf-c4c9-4b77-bb45-204cd05eb173	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_manage-authorization}	manage-authorization	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
95a36a78-8686-4528-a66e-9d0fac949f09	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_query-users}	query-users	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
374fcf09-a28e-46fe-83fd-c39da332212a	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_query-clients}	query-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
609716fd-5ee6-4075-bb03-e9ff43c15ac7	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_query-realms}	query-realms	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
554ece2a-9743-40fa-9f1a-c021a155e946	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_query-groups}	query-groups	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
49a7a532-2417-46b1-bef2-47042cce81ed	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_view-profile}	view-profile	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
624216f9-9ba9-4813-bb4f-2fdd008b5645	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_manage-account}	manage-account	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
1ab458ee-e7d8-4871-a5e0-a04cd5ea883d	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_manage-account-links}	manage-account-links	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
ac2baf54-c67c-4d84-8588-2f28ebf63e11	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_view-applications}	view-applications	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
bafbf1e0-6924-4625-907d-61bc2dc0fffa	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_view-consent}	view-consent	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
99fa23b7-e3be-49aa-8773-01ba5451d42f	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_manage-consent}	manage-consent	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
f38781bf-4267-499e-bdac-91df42e62300	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_view-groups}	view-groups	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
f4148dc3-6dbe-44f3-b0fa-62c0f9118660	45110c87-41e9-4cf3-b4ed-958db7b94b24	t	${role_delete-account}	delete-account	973d96fb-e7bb-493a-b27f-0020b0da1731	45110c87-41e9-4cf3-b4ed-958db7b94b24	\N
c4c40f7b-62eb-4a9a-a55b-63d954fd8ce9	ab322188-67b9-478c-9c88-4141b48a9e12	t	${role_read-token}	read-token	973d96fb-e7bb-493a-b27f-0020b0da1731	ab322188-67b9-478c-9c88-4141b48a9e12	\N
943144ac-34b5-4715-9d66-8a3da931b15a	a4965dcb-4c1d-4086-a24e-69d6357f614b	t	${role_impersonation}	impersonation	973d96fb-e7bb-493a-b27f-0020b0da1731	a4965dcb-4c1d-4086-a24e-69d6357f614b	\N
06fe37f5-4b92-45e5-80ad-498929412bfb	973d96fb-e7bb-493a-b27f-0020b0da1731	f	${role_offline-access}	offline_access	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	\N
a7f248b9-9ca5-4e78-8a17-9fb9f2dfe98e	973d96fb-e7bb-493a-b27f-0020b0da1731	f	${role_uma_authorization}	uma_authorization	973d96fb-e7bb-493a-b27f-0020b0da1731	\N	\N
40ba6aab-743b-4a42-88be-64c606013207	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f	${role_default-roles}	default-roles-quinta-ypua	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N	\N
6e389bb3-ce7c-4692-b5b2-07b3c7dc8f12	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_create-client}	create-client	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
f6753958-1377-446b-86f9-36ac4f21a0b8	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-realm}	view-realm	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
31d48bd4-6bd9-48fc-ad9b-80039775b22c	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-users}	view-users	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
69dff334-6f22-4793-a201-f0ed5d8132be	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-clients}	view-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
e5b48493-d112-4aef-9e81-33489c01c97f	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-events}	view-events	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
81e028c1-02df-410a-a4fd-616c5edbcd09	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-identity-providers}	view-identity-providers	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
76cb6387-3e91-431a-a856-b6fb4e4701bd	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_view-authorization}	view-authorization	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
b1326db7-413f-4021-9413-78c1c6a0d5d0	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-realm}	manage-realm	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
4447ed1d-d58d-4ed9-ad86-6f71cc921da5	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-users}	manage-users	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
09d67c9e-d80b-418e-99ed-d62d074e86cc	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-clients}	manage-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
e1bcc396-4bc3-4326-a75a-edc59c25778d	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-events}	manage-events	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
3723b752-9dc2-4172-afb3-1135dffb945c	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-identity-providers}	manage-identity-providers	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
8f18532c-0900-413b-bed3-21719a56cabb	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_manage-authorization}	manage-authorization	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
627cc7b2-f228-4eb2-8893-e2e445d3bb14	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_query-users}	query-users	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
0ef24ca6-2eb2-42ff-bc6e-1effdf3d37bc	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_query-clients}	query-clients	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
6c8a40bb-6f5d-4167-a4dd-3fdfaee65203	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_query-realms}	query-realms	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
9eb455d4-30d1-40d0-acec-1fe09fc4bdfb	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_query-groups}	query-groups	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_realm-admin}	realm-admin	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
2dfb0f70-a2c3-46d1-9a53-9c5d00138a92	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_create-client}	create-client	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
b1bf9883-5115-4bdb-8dfe-ae85841fbbd1	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-realm}	view-realm	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
5b37f676-c63d-4f38-bd8f-c46a0b9e9577	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-users}	view-users	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
bfc43dc6-920b-46c1-a1c1-a11f5be1022d	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-clients}	view-clients	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
aee34c51-3a24-495d-aabb-09d829552ede	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-events}	view-events	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
ac694953-e165-443e-8716-06860a9a7b24	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-identity-providers}	view-identity-providers	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
f03e60ff-d5e0-4a45-a077-c5ffaa6acaf9	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_view-authorization}	view-authorization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
334b38e6-7e76-4529-babc-4f5b9d070041	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-realm}	manage-realm	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
89ff538a-8fcd-4a99-a186-0fd5218c1df2	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-users}	manage-users	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
bd2b7a75-fdcd-4ebb-9976-a684f92b6445	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-clients}	manage-clients	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
8f9f1a36-6669-4e38-b6b3-3a63756dc855	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-events}	manage-events	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
45c183f9-cddb-4ffc-83f6-ebfbef307480	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-identity-providers}	manage-identity-providers	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
d8079922-fd5b-4872-8c09-1d0fe58e5c56	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_manage-authorization}	manage-authorization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
8b61eb95-be8d-4bfd-95e1-4600c0978efd	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_query-users}	query-users	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
5b8650bf-8ad3-4bc4-b6e6-e499e0f394ef	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_query-clients}	query-clients	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
2aef1606-6d21-4edb-a516-7da893bbea9d	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_query-realms}	query-realms	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
1ac05c5c-2b14-4df0-ba34-cae4148f86c8	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_query-groups}	query-groups	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
1c427514-2ed9-484f-862f-1eee7968e512	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_view-profile}	view-profile	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
78c70b68-43b3-41b9-bb50-1631aa1a3cf2	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_manage-account}	manage-account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
18e65ae0-68e4-441a-9858-453bd98af48c	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_manage-account-links}	manage-account-links	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
7758a27e-9422-458e-b537-7fb08a87acce	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_view-applications}	view-applications	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
45430fde-c450-4a8f-91d6-2321b78548c3	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_view-consent}	view-consent	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
f8eec290-372a-4c51-bb3e-977e144384f6	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_manage-consent}	manage-consent	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
f18906f5-9b55-4ba8-b2c7-6a4b98c56049	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_view-groups}	view-groups	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
698d02ba-6e2b-4abd-9d9c-04b96c946e92	069afb20-1212-44a1-bd8b-4ed84b6e9003	t	${role_delete-account}	delete-account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	069afb20-1212-44a1-bd8b-4ed84b6e9003	\N
7bb41f93-f2fc-4460-aea4-f81e8b8d248f	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	t	${role_impersonation}	impersonation	973d96fb-e7bb-493a-b27f-0020b0da1731	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	\N
0cd820a1-99f7-4803-b108-c65c31283370	ba03050c-4820-4344-bf37-143e5570eb9f	t	${role_impersonation}	impersonation	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ba03050c-4820-4344-bf37-143e5570eb9f	\N
b7bf805d-1e2d-48bb-9669-8d51bde19f79	19ef52a7-5edc-4169-bf05-093c7a677859	t	${role_read-token}	read-token	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	19ef52a7-5edc-4169-bf05-093c7a677859	\N
cf64d75b-bb8f-4540-b035-bd0b1442c137	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f	${role_offline-access}	offline_access	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N	\N
363b87c8-67d6-44ed-ab88-df4617d12ae1	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f	${role_uma_authorization}	uma_authorization	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	\N	\N
5bb3511e-1152-4201-9c7d-b9ebb8be5dc4	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		cliente-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
3363b1d2-f88a-4bf0-8a91-21ad758244fd	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		cliente-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
a1478a9a-4c46-4fb6-b2e9-e9029c3cd48e	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		amenidade-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
acf45de6-c155-40f6-aae6-88f3795dd996	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		amenidade-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
148597b7-2ee7-4a3b-958a-e229f3e85480	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		complemento-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
20dff205-6580-416a-8c6d-00775bccab09	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		complemento-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
30fec081-b3ba-4227-9cb9-5468bb7f6c3c	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		cupom-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
1689a33c-7964-41d2-abac-fe9ae69e18b1	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		cupom-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
1be57665-c03c-4a6b-9b4f-fb7a58afb22c	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		quarto-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
0f83a87b-4cea-439d-bcce-82ffda859348	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		quarto-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
28a1c92c-285c-45b9-9979-b203371cef94	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		reserva-operacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
8f49165f-3d69-4978-b4bc-e4b99267c2f9	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		reserva-visualizacao	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
23d9c00b-0dde-4370-a74b-b7052b613be7	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	t		admin	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.migration_model (id, version, update_time) FROM stdin;
yiukq	26.1.0	1773097569
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
cc25522b-7b50-40f2-a667-4a4d306523f3	d13aa8eb-2614-4053-a25a-bf35f9c9a73d	0	1779143370	{"authMethod":"openid-connect","redirectUri":"http://localhost:14082/admin/master/console/","notes":{"clientId":"d13aa8eb-2614-4053-a25a-bf35f9c9a73d","iss":"http://localhost:14082/realms/master","startedAt":"1779143095","response_type":"code","level-of-authentication":"-1","code_challenge_method":"S256","nonce":"c61119e3-fcd4-486a-8796-4a82d214532b","response_mode":"query","scope":"openid","userSessionStartedAt":"1779143095","redirect_uri":"http://localhost:14082/admin/master/console/","state":"efc53ef1-d6c1-44d5-8cee-2e502143610b","code_challenge":"O5SpBr3Ago1TwpWRg3JUPcaRl7NQEM_0DCUCRsRq_pw"}}	local	local	3
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
cc25522b-7b50-40f2-a667-4a4d306523f3	5727be85-b2de-4841-9e4f-4381021fa33e	973d96fb-e7bb-493a-b27f-0020b0da1731	1779143095	0	{"ipAddress":"172.18.0.1","authMethod":"openid-connect","rememberMe":false,"started":0,"notes":{"KC_DEVICE_NOTE":"eyJpcEFkZHJlc3MiOiIxNzIuMTguMC4xIiwib3MiOiJMaW51eCIsIm9zVmVyc2lvbiI6IlVua25vd24iLCJicm93c2VyIjoiRmlyZWZveC8xNDkuMCIsImRldmljZSI6Ik90aGVyIiwibGFzdEFjY2VzcyI6MCwibW9iaWxlIjpmYWxzZX0=","AUTH_TIME":"1779143095","authenticators-completed":"{\\"cf2cecd6-9384-455c-a84f-900cc9c023ff\\":1779143095}"},"state":"LOGGED_IN"}	1779143370	\N	3
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.org (id, enabled, realm_id, group_id, name, description, alias, redirect_url) FROM stdin;
\.


--
-- Data for Name: org_domain; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.org_domain (id, name, verified, org_id) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
514b8c4e-61cb-4566-9694-fdb42444e941	audience resolve	openid-connect	oidc-audience-resolve-mapper	f3b08e34-80ce-405d-b6e7-0c35eda81170	\N
a320c51e-61df-43d3-bb9b-21e7e599d354	locale	openid-connect	oidc-usermodel-attribute-mapper	d13aa8eb-2614-4053-a25a-bf35f9c9a73d	\N
13aabf32-bf5d-4098-9411-d0eed1dbe170	role list	saml	saml-role-list-mapper	\N	62c0f6b5-5e6f-437b-9879-862536176c30
a37d6156-27ec-4284-8558-22b7f18062fd	organization	saml	saml-organization-membership-mapper	\N	133e76b9-1121-4c1d-aed9-24bf3a8ca799
32c6c745-0f21-47fe-a0ef-1a5feeb037a8	full name	openid-connect	oidc-full-name-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
9cbdeea0-6275-4866-9a3b-5c381365327a	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
59fe8440-07e2-40d5-b13e-2b344241cef2	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
aec3d906-a03a-43d5-a827-5a4b4066b143	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
3108cd27-faa9-4525-a96f-597ce1f89618	username	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
a6c397dd-f2d5-4640-a5f3-5f730d66734e	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
cc2c68a8-2288-4e67-bfd2-af13c9de9732	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	website	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
8c6ed471-1845-47b9-926e-e6a67c65d921	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
1ad991bb-ff7b-479d-980b-441e6821beea	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
a1009b28-ef45-4441-a44c-b7d970292415	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
e5005531-66b5-4b7a-a0b5-ae5c78116cae	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
a5cbca7a-fed9-4727-a52b-32ac12d800f1	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	1ee33799-b762-4af3-a32e-102ca6062249
996d724d-ecff-4ca9-8a9a-170361598e22	email	openid-connect	oidc-usermodel-attribute-mapper	\N	4e5546b8-2e8d-435d-a164-19978835c118
e7481107-24e3-43df-b740-8476adae3bef	email verified	openid-connect	oidc-usermodel-property-mapper	\N	4e5546b8-2e8d-435d-a164-19978835c118
a5389001-7acf-4a22-947e-1b0454aba228	address	openid-connect	oidc-address-mapper	\N	0579c7ef-131c-44dd-9879-6d9fd5605507
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	405408a2-31c1-4e69-ae4d-edb75d4d335f
ce77dcd5-23ea-4c42-be0c-0177237ee5df	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	405408a2-31c1-4e69-ae4d-edb75d4d335f
ff70e977-ee52-4c34-a236-7eceda858d1f	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	b1c35655-b9d3-426a-8b84-578aa7df9550
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	b1c35655-b9d3-426a-8b84-578aa7df9550
30736554-744e-4817-803d-459da6df2541	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	b1c35655-b9d3-426a-8b84-578aa7df9550
2221c34b-a7a2-47ce-b70e-8218cf111da7	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	5fa39419-de3c-4f95-8889-70143742d2ce
f7638d1c-1d87-4a14-9e71-d56933e50055	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	a9da574d-4857-4a68-93b8-1235e15fcccc
310b4aa0-c750-47e1-8a03-f4ef151502d1	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	a9da574d-4857-4a68-93b8-1235e15fcccc
b9b42d9a-770d-477c-9478-1824b4531425	acr loa level	openid-connect	oidc-acr-mapper	\N	3ad51afb-b9b9-4451-9a1c-54a80c6678ba
f9c52943-e236-4d21-9980-d2c3190649a8	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	bab57543-7701-45d5-8ae3-82024fa8dd5a
939a4fb3-8c28-46d2-a87b-27bd5c70dd06	sub	openid-connect	oidc-sub-mapper	\N	bab57543-7701-45d5-8ae3-82024fa8dd5a
5089dfc7-16d6-4567-b309-878ac1fd497a	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	\N	14548990-f86f-4a01-aa96-334bf82c6ba2
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	\N	14548990-f86f-4a01-aa96-334bf82c6ba2
13882fb1-860f-45f3-a316-eeb05aa626ce	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	\N	14548990-f86f-4a01-aa96-334bf82c6ba2
38dbebc7-f149-4266-96a9-28d462e05834	organization	openid-connect	oidc-organization-membership-mapper	\N	7c2cf2f9-130a-4bf3-aa63-357e94a1ef74
6b11d7d2-9feb-4c08-aa33-be09987513ed	audience resolve	openid-connect	oidc-audience-resolve-mapper	9ce4bbb9-10bf-4b13-8356-225e00c27007	\N
710f7400-c4bf-41f1-a8c4-cf3ca9257521	role list	saml	saml-role-list-mapper	\N	070dcf44-8b37-4d44-ba36-180871706c7b
b147cc00-69a0-4522-ab83-9631ecca5a2c	organization	saml	saml-organization-membership-mapper	\N	69508c30-cabe-4cd8-b8ea-d1067db97f42
d351b1b5-7a7c-4ead-ac1a-4f0a387d42d3	full name	openid-connect	oidc-full-name-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
cf7d7139-2a5b-4552-9876-638e1fe544fa	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
82917095-06c6-4fd5-ac85-1aeb40661798	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
367babe6-ba99-400d-815b-1bd4947267e0	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
88bdbc9c-4d50-4185-a7df-99210d3a9561	username	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
742e02c1-58a8-4091-82d0-2a14bcfebddf	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
2d3015c8-27f7-4a52-a74b-3d33df14a28d	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
d01af460-c4ac-43f2-b357-78680fe3b01c	website	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
06c9969e-ab51-47ab-9cfe-212355daab74	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
f099d32a-3fe8-4896-a060-90ecf339c679	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	dc4f3172-7fa8-4a40-9f96-871d134212ce
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	email	openid-connect	oidc-usermodel-attribute-mapper	\N	f05f9faf-6a40-4228-8afd-3bb9eadefa9a
f34bd7d9-5dd8-4e31-8292-7811ad108cff	email verified	openid-connect	oidc-usermodel-property-mapper	\N	f05f9faf-6a40-4228-8afd-3bb9eadefa9a
688762ae-7775-4a32-9fd6-5966f7a81d94	address	openid-connect	oidc-address-mapper	\N	34005ce2-fcdb-45e3-a007-d3fb35796dfd
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	38635cee-3db8-49e7-bb41-37b4b70ffefd
54435d1e-47dd-458a-8809-84fbc6aefa31	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	38635cee-3db8-49e7-bb41-37b4b70ffefd
018b1d6f-9ceb-4d63-a252-345500adb858	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	cf806293-5721-4f38-9044-726dd7b53b62
cd1471f4-2cd2-482d-8e81-3f95873d142e	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	cf806293-5721-4f38-9044-726dd7b53b62
da931420-1529-4088-b697-5453382b2a8b	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	cf806293-5721-4f38-9044-726dd7b53b62
c6e65862-ba9f-4524-8ac4-3da6d1ceaf1c	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	73fbb70d-f9b2-4d22-bd90-91a1c580202f
b77711ff-0371-43f1-86de-9c47fcc7e99d	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	73475f69-7535-4746-b4c6-af4db747fb8a
264f9582-e4e0-4037-801b-ea7f426459f7	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	73475f69-7535-4746-b4c6-af4db747fb8a
61814488-3e53-4739-8594-c6f16b2ab241	acr loa level	openid-connect	oidc-acr-mapper	\N	7cfe80d3-0bee-4f22-9b0f-2f264a8936bd
8760d061-a719-471c-b1b1-6a620e1d7b54	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	4c0c22e0-1a30-4ccb-8a60-8552041ab07a
0c382a53-d069-4148-8aa2-499c6daaf670	sub	openid-connect	oidc-sub-mapper	\N	4c0c22e0-1a30-4ccb-8a60-8552041ab07a
4fba9865-c6c4-45b3-af44-19115053461c	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	\N	1efe54a4-0d74-42a0-b30e-9261d0d3626b
47209101-b6b3-445e-8034-59dd1d209f53	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	\N	1efe54a4-0d74-42a0-b30e-9261d0d3626b
a5715743-7e51-4332-926e-02677799bdf4	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	\N	1efe54a4-0d74-42a0-b30e-9261d0d3626b
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	organization	openid-connect	oidc-organization-membership-mapper	\N	20658153-e9d8-4fdd-bdee-764461e3446e
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	locale	openid-connect	oidc-usermodel-attribute-mapper	bfcaa195-cce4-416f-8626-f0a3b93e381b	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
a320c51e-61df-43d3-bb9b-21e7e599d354	true	introspection.token.claim
a320c51e-61df-43d3-bb9b-21e7e599d354	true	userinfo.token.claim
a320c51e-61df-43d3-bb9b-21e7e599d354	locale	user.attribute
a320c51e-61df-43d3-bb9b-21e7e599d354	true	id.token.claim
a320c51e-61df-43d3-bb9b-21e7e599d354	true	access.token.claim
a320c51e-61df-43d3-bb9b-21e7e599d354	locale	claim.name
a320c51e-61df-43d3-bb9b-21e7e599d354	String	jsonType.label
13aabf32-bf5d-4098-9411-d0eed1dbe170	false	single
13aabf32-bf5d-4098-9411-d0eed1dbe170	Basic	attribute.nameformat
13aabf32-bf5d-4098-9411-d0eed1dbe170	Role	attribute.name
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	true	introspection.token.claim
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	true	userinfo.token.claim
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	lastName	user.attribute
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	true	id.token.claim
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	true	access.token.claim
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	family_name	claim.name
0001cd79-079a-4a4a-bb9e-f2cfc25cd93e	String	jsonType.label
1ad991bb-ff7b-479d-980b-441e6821beea	true	introspection.token.claim
1ad991bb-ff7b-479d-980b-441e6821beea	true	userinfo.token.claim
1ad991bb-ff7b-479d-980b-441e6821beea	birthdate	user.attribute
1ad991bb-ff7b-479d-980b-441e6821beea	true	id.token.claim
1ad991bb-ff7b-479d-980b-441e6821beea	true	access.token.claim
1ad991bb-ff7b-479d-980b-441e6821beea	birthdate	claim.name
1ad991bb-ff7b-479d-980b-441e6821beea	String	jsonType.label
3108cd27-faa9-4525-a96f-597ce1f89618	true	introspection.token.claim
3108cd27-faa9-4525-a96f-597ce1f89618	true	userinfo.token.claim
3108cd27-faa9-4525-a96f-597ce1f89618	username	user.attribute
3108cd27-faa9-4525-a96f-597ce1f89618	true	id.token.claim
3108cd27-faa9-4525-a96f-597ce1f89618	true	access.token.claim
3108cd27-faa9-4525-a96f-597ce1f89618	preferred_username	claim.name
3108cd27-faa9-4525-a96f-597ce1f89618	String	jsonType.label
32c6c745-0f21-47fe-a0ef-1a5feeb037a8	true	introspection.token.claim
32c6c745-0f21-47fe-a0ef-1a5feeb037a8	true	userinfo.token.claim
32c6c745-0f21-47fe-a0ef-1a5feeb037a8	true	id.token.claim
32c6c745-0f21-47fe-a0ef-1a5feeb037a8	true	access.token.claim
59fe8440-07e2-40d5-b13e-2b344241cef2	true	introspection.token.claim
59fe8440-07e2-40d5-b13e-2b344241cef2	true	userinfo.token.claim
59fe8440-07e2-40d5-b13e-2b344241cef2	middleName	user.attribute
59fe8440-07e2-40d5-b13e-2b344241cef2	true	id.token.claim
59fe8440-07e2-40d5-b13e-2b344241cef2	true	access.token.claim
59fe8440-07e2-40d5-b13e-2b344241cef2	middle_name	claim.name
59fe8440-07e2-40d5-b13e-2b344241cef2	String	jsonType.label
8c6ed471-1845-47b9-926e-e6a67c65d921	true	introspection.token.claim
8c6ed471-1845-47b9-926e-e6a67c65d921	true	userinfo.token.claim
8c6ed471-1845-47b9-926e-e6a67c65d921	gender	user.attribute
8c6ed471-1845-47b9-926e-e6a67c65d921	true	id.token.claim
8c6ed471-1845-47b9-926e-e6a67c65d921	true	access.token.claim
8c6ed471-1845-47b9-926e-e6a67c65d921	gender	claim.name
8c6ed471-1845-47b9-926e-e6a67c65d921	String	jsonType.label
9cbdeea0-6275-4866-9a3b-5c381365327a	true	introspection.token.claim
9cbdeea0-6275-4866-9a3b-5c381365327a	true	userinfo.token.claim
9cbdeea0-6275-4866-9a3b-5c381365327a	firstName	user.attribute
9cbdeea0-6275-4866-9a3b-5c381365327a	true	id.token.claim
9cbdeea0-6275-4866-9a3b-5c381365327a	true	access.token.claim
9cbdeea0-6275-4866-9a3b-5c381365327a	given_name	claim.name
9cbdeea0-6275-4866-9a3b-5c381365327a	String	jsonType.label
a1009b28-ef45-4441-a44c-b7d970292415	true	introspection.token.claim
a1009b28-ef45-4441-a44c-b7d970292415	true	userinfo.token.claim
a1009b28-ef45-4441-a44c-b7d970292415	zoneinfo	user.attribute
a1009b28-ef45-4441-a44c-b7d970292415	true	id.token.claim
a1009b28-ef45-4441-a44c-b7d970292415	true	access.token.claim
a1009b28-ef45-4441-a44c-b7d970292415	zoneinfo	claim.name
a1009b28-ef45-4441-a44c-b7d970292415	String	jsonType.label
a5cbca7a-fed9-4727-a52b-32ac12d800f1	true	introspection.token.claim
a5cbca7a-fed9-4727-a52b-32ac12d800f1	true	userinfo.token.claim
a5cbca7a-fed9-4727-a52b-32ac12d800f1	updatedAt	user.attribute
a5cbca7a-fed9-4727-a52b-32ac12d800f1	true	id.token.claim
a5cbca7a-fed9-4727-a52b-32ac12d800f1	true	access.token.claim
a5cbca7a-fed9-4727-a52b-32ac12d800f1	updated_at	claim.name
a5cbca7a-fed9-4727-a52b-32ac12d800f1	long	jsonType.label
a6c397dd-f2d5-4640-a5f3-5f730d66734e	true	introspection.token.claim
a6c397dd-f2d5-4640-a5f3-5f730d66734e	true	userinfo.token.claim
a6c397dd-f2d5-4640-a5f3-5f730d66734e	profile	user.attribute
a6c397dd-f2d5-4640-a5f3-5f730d66734e	true	id.token.claim
a6c397dd-f2d5-4640-a5f3-5f730d66734e	true	access.token.claim
a6c397dd-f2d5-4640-a5f3-5f730d66734e	profile	claim.name
a6c397dd-f2d5-4640-a5f3-5f730d66734e	String	jsonType.label
aec3d906-a03a-43d5-a827-5a4b4066b143	true	introspection.token.claim
aec3d906-a03a-43d5-a827-5a4b4066b143	true	userinfo.token.claim
aec3d906-a03a-43d5-a827-5a4b4066b143	nickname	user.attribute
aec3d906-a03a-43d5-a827-5a4b4066b143	true	id.token.claim
aec3d906-a03a-43d5-a827-5a4b4066b143	true	access.token.claim
aec3d906-a03a-43d5-a827-5a4b4066b143	nickname	claim.name
aec3d906-a03a-43d5-a827-5a4b4066b143	String	jsonType.label
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	true	introspection.token.claim
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	true	userinfo.token.claim
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	website	user.attribute
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	true	id.token.claim
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	true	access.token.claim
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	website	claim.name
c62c31e7-1afa-403d-a392-7a62b5e9cb1b	String	jsonType.label
cc2c68a8-2288-4e67-bfd2-af13c9de9732	true	introspection.token.claim
cc2c68a8-2288-4e67-bfd2-af13c9de9732	true	userinfo.token.claim
cc2c68a8-2288-4e67-bfd2-af13c9de9732	picture	user.attribute
cc2c68a8-2288-4e67-bfd2-af13c9de9732	true	id.token.claim
cc2c68a8-2288-4e67-bfd2-af13c9de9732	true	access.token.claim
cc2c68a8-2288-4e67-bfd2-af13c9de9732	picture	claim.name
cc2c68a8-2288-4e67-bfd2-af13c9de9732	String	jsonType.label
e5005531-66b5-4b7a-a0b5-ae5c78116cae	true	introspection.token.claim
e5005531-66b5-4b7a-a0b5-ae5c78116cae	true	userinfo.token.claim
e5005531-66b5-4b7a-a0b5-ae5c78116cae	locale	user.attribute
e5005531-66b5-4b7a-a0b5-ae5c78116cae	true	id.token.claim
e5005531-66b5-4b7a-a0b5-ae5c78116cae	true	access.token.claim
e5005531-66b5-4b7a-a0b5-ae5c78116cae	locale	claim.name
e5005531-66b5-4b7a-a0b5-ae5c78116cae	String	jsonType.label
996d724d-ecff-4ca9-8a9a-170361598e22	true	introspection.token.claim
996d724d-ecff-4ca9-8a9a-170361598e22	true	userinfo.token.claim
996d724d-ecff-4ca9-8a9a-170361598e22	email	user.attribute
996d724d-ecff-4ca9-8a9a-170361598e22	true	id.token.claim
996d724d-ecff-4ca9-8a9a-170361598e22	true	access.token.claim
996d724d-ecff-4ca9-8a9a-170361598e22	email	claim.name
996d724d-ecff-4ca9-8a9a-170361598e22	String	jsonType.label
e7481107-24e3-43df-b740-8476adae3bef	true	introspection.token.claim
e7481107-24e3-43df-b740-8476adae3bef	true	userinfo.token.claim
e7481107-24e3-43df-b740-8476adae3bef	emailVerified	user.attribute
e7481107-24e3-43df-b740-8476adae3bef	true	id.token.claim
e7481107-24e3-43df-b740-8476adae3bef	true	access.token.claim
e7481107-24e3-43df-b740-8476adae3bef	email_verified	claim.name
e7481107-24e3-43df-b740-8476adae3bef	boolean	jsonType.label
a5389001-7acf-4a22-947e-1b0454aba228	formatted	user.attribute.formatted
a5389001-7acf-4a22-947e-1b0454aba228	country	user.attribute.country
a5389001-7acf-4a22-947e-1b0454aba228	true	introspection.token.claim
a5389001-7acf-4a22-947e-1b0454aba228	postal_code	user.attribute.postal_code
a5389001-7acf-4a22-947e-1b0454aba228	true	userinfo.token.claim
a5389001-7acf-4a22-947e-1b0454aba228	street	user.attribute.street
a5389001-7acf-4a22-947e-1b0454aba228	true	id.token.claim
a5389001-7acf-4a22-947e-1b0454aba228	region	user.attribute.region
a5389001-7acf-4a22-947e-1b0454aba228	true	access.token.claim
a5389001-7acf-4a22-947e-1b0454aba228	locality	user.attribute.locality
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	true	introspection.token.claim
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	true	userinfo.token.claim
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	phoneNumber	user.attribute
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	true	id.token.claim
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	true	access.token.claim
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	phone_number	claim.name
98c70ab9-cf2d-4cc2-bf38-d4642a8b9101	String	jsonType.label
ce77dcd5-23ea-4c42-be0c-0177237ee5df	true	introspection.token.claim
ce77dcd5-23ea-4c42-be0c-0177237ee5df	true	userinfo.token.claim
ce77dcd5-23ea-4c42-be0c-0177237ee5df	phoneNumberVerified	user.attribute
ce77dcd5-23ea-4c42-be0c-0177237ee5df	true	id.token.claim
ce77dcd5-23ea-4c42-be0c-0177237ee5df	true	access.token.claim
ce77dcd5-23ea-4c42-be0c-0177237ee5df	phone_number_verified	claim.name
ce77dcd5-23ea-4c42-be0c-0177237ee5df	boolean	jsonType.label
30736554-744e-4817-803d-459da6df2541	true	introspection.token.claim
30736554-744e-4817-803d-459da6df2541	true	access.token.claim
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	true	introspection.token.claim
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	true	multivalued
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	foo	user.attribute
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	true	access.token.claim
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	resource_access.${client_id}.roles	claim.name
bf820ed4-c315-49eb-88c6-6fa950a2fa3d	String	jsonType.label
ff70e977-ee52-4c34-a236-7eceda858d1f	true	introspection.token.claim
ff70e977-ee52-4c34-a236-7eceda858d1f	true	multivalued
ff70e977-ee52-4c34-a236-7eceda858d1f	foo	user.attribute
ff70e977-ee52-4c34-a236-7eceda858d1f	true	access.token.claim
ff70e977-ee52-4c34-a236-7eceda858d1f	realm_access.roles	claim.name
ff70e977-ee52-4c34-a236-7eceda858d1f	String	jsonType.label
2221c34b-a7a2-47ce-b70e-8218cf111da7	true	introspection.token.claim
2221c34b-a7a2-47ce-b70e-8218cf111da7	true	access.token.claim
310b4aa0-c750-47e1-8a03-f4ef151502d1	true	introspection.token.claim
310b4aa0-c750-47e1-8a03-f4ef151502d1	true	multivalued
310b4aa0-c750-47e1-8a03-f4ef151502d1	foo	user.attribute
310b4aa0-c750-47e1-8a03-f4ef151502d1	true	id.token.claim
310b4aa0-c750-47e1-8a03-f4ef151502d1	true	access.token.claim
310b4aa0-c750-47e1-8a03-f4ef151502d1	groups	claim.name
310b4aa0-c750-47e1-8a03-f4ef151502d1	String	jsonType.label
f7638d1c-1d87-4a14-9e71-d56933e50055	true	introspection.token.claim
f7638d1c-1d87-4a14-9e71-d56933e50055	true	userinfo.token.claim
f7638d1c-1d87-4a14-9e71-d56933e50055	username	user.attribute
f7638d1c-1d87-4a14-9e71-d56933e50055	true	id.token.claim
f7638d1c-1d87-4a14-9e71-d56933e50055	true	access.token.claim
f7638d1c-1d87-4a14-9e71-d56933e50055	upn	claim.name
f7638d1c-1d87-4a14-9e71-d56933e50055	String	jsonType.label
b9b42d9a-770d-477c-9478-1824b4531425	true	introspection.token.claim
b9b42d9a-770d-477c-9478-1824b4531425	true	id.token.claim
b9b42d9a-770d-477c-9478-1824b4531425	true	access.token.claim
939a4fb3-8c28-46d2-a87b-27bd5c70dd06	true	introspection.token.claim
939a4fb3-8c28-46d2-a87b-27bd5c70dd06	true	access.token.claim
f9c52943-e236-4d21-9980-d2c3190649a8	AUTH_TIME	user.session.note
f9c52943-e236-4d21-9980-d2c3190649a8	true	introspection.token.claim
f9c52943-e236-4d21-9980-d2c3190649a8	true	id.token.claim
f9c52943-e236-4d21-9980-d2c3190649a8	true	access.token.claim
f9c52943-e236-4d21-9980-d2c3190649a8	auth_time	claim.name
f9c52943-e236-4d21-9980-d2c3190649a8	long	jsonType.label
13882fb1-860f-45f3-a316-eeb05aa626ce	clientAddress	user.session.note
13882fb1-860f-45f3-a316-eeb05aa626ce	true	introspection.token.claim
13882fb1-860f-45f3-a316-eeb05aa626ce	true	id.token.claim
13882fb1-860f-45f3-a316-eeb05aa626ce	true	access.token.claim
13882fb1-860f-45f3-a316-eeb05aa626ce	clientAddress	claim.name
13882fb1-860f-45f3-a316-eeb05aa626ce	String	jsonType.label
5089dfc7-16d6-4567-b309-878ac1fd497a	client_id	user.session.note
5089dfc7-16d6-4567-b309-878ac1fd497a	true	introspection.token.claim
5089dfc7-16d6-4567-b309-878ac1fd497a	true	id.token.claim
5089dfc7-16d6-4567-b309-878ac1fd497a	true	access.token.claim
5089dfc7-16d6-4567-b309-878ac1fd497a	client_id	claim.name
5089dfc7-16d6-4567-b309-878ac1fd497a	String	jsonType.label
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	clientHost	user.session.note
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	true	introspection.token.claim
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	true	id.token.claim
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	true	access.token.claim
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	clientHost	claim.name
6b5d8980-0e6c-4e7f-9a81-199d0cc607f1	String	jsonType.label
38dbebc7-f149-4266-96a9-28d462e05834	true	introspection.token.claim
38dbebc7-f149-4266-96a9-28d462e05834	true	multivalued
38dbebc7-f149-4266-96a9-28d462e05834	true	id.token.claim
38dbebc7-f149-4266-96a9-28d462e05834	true	access.token.claim
38dbebc7-f149-4266-96a9-28d462e05834	organization	claim.name
38dbebc7-f149-4266-96a9-28d462e05834	String	jsonType.label
710f7400-c4bf-41f1-a8c4-cf3ca9257521	false	single
710f7400-c4bf-41f1-a8c4-cf3ca9257521	Basic	attribute.nameformat
710f7400-c4bf-41f1-a8c4-cf3ca9257521	Role	attribute.name
06c9969e-ab51-47ab-9cfe-212355daab74	true	introspection.token.claim
06c9969e-ab51-47ab-9cfe-212355daab74	true	userinfo.token.claim
06c9969e-ab51-47ab-9cfe-212355daab74	birthdate	user.attribute
06c9969e-ab51-47ab-9cfe-212355daab74	true	id.token.claim
06c9969e-ab51-47ab-9cfe-212355daab74	true	access.token.claim
06c9969e-ab51-47ab-9cfe-212355daab74	birthdate	claim.name
06c9969e-ab51-47ab-9cfe-212355daab74	String	jsonType.label
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	true	introspection.token.claim
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	true	userinfo.token.claim
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	gender	user.attribute
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	true	id.token.claim
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	true	access.token.claim
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	gender	claim.name
0b5bf732-6bc9-490c-9639-b1202c8cbf6b	String	jsonType.label
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	true	introspection.token.claim
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	true	userinfo.token.claim
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	firstName	user.attribute
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	true	id.token.claim
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	true	access.token.claim
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	given_name	claim.name
1e069f37-31aa-470c-aa85-8b33cbdfeeb7	String	jsonType.label
2d3015c8-27f7-4a52-a74b-3d33df14a28d	true	introspection.token.claim
2d3015c8-27f7-4a52-a74b-3d33df14a28d	true	userinfo.token.claim
2d3015c8-27f7-4a52-a74b-3d33df14a28d	picture	user.attribute
2d3015c8-27f7-4a52-a74b-3d33df14a28d	true	id.token.claim
2d3015c8-27f7-4a52-a74b-3d33df14a28d	true	access.token.claim
2d3015c8-27f7-4a52-a74b-3d33df14a28d	picture	claim.name
2d3015c8-27f7-4a52-a74b-3d33df14a28d	String	jsonType.label
367babe6-ba99-400d-815b-1bd4947267e0	true	introspection.token.claim
367babe6-ba99-400d-815b-1bd4947267e0	true	userinfo.token.claim
367babe6-ba99-400d-815b-1bd4947267e0	nickname	user.attribute
367babe6-ba99-400d-815b-1bd4947267e0	true	id.token.claim
367babe6-ba99-400d-815b-1bd4947267e0	true	access.token.claim
367babe6-ba99-400d-815b-1bd4947267e0	nickname	claim.name
367babe6-ba99-400d-815b-1bd4947267e0	String	jsonType.label
742e02c1-58a8-4091-82d0-2a14bcfebddf	true	introspection.token.claim
742e02c1-58a8-4091-82d0-2a14bcfebddf	true	userinfo.token.claim
742e02c1-58a8-4091-82d0-2a14bcfebddf	profile	user.attribute
742e02c1-58a8-4091-82d0-2a14bcfebddf	true	id.token.claim
742e02c1-58a8-4091-82d0-2a14bcfebddf	true	access.token.claim
742e02c1-58a8-4091-82d0-2a14bcfebddf	profile	claim.name
742e02c1-58a8-4091-82d0-2a14bcfebddf	String	jsonType.label
82917095-06c6-4fd5-ac85-1aeb40661798	true	introspection.token.claim
82917095-06c6-4fd5-ac85-1aeb40661798	true	userinfo.token.claim
82917095-06c6-4fd5-ac85-1aeb40661798	middleName	user.attribute
82917095-06c6-4fd5-ac85-1aeb40661798	true	id.token.claim
82917095-06c6-4fd5-ac85-1aeb40661798	true	access.token.claim
82917095-06c6-4fd5-ac85-1aeb40661798	middle_name	claim.name
82917095-06c6-4fd5-ac85-1aeb40661798	String	jsonType.label
88bdbc9c-4d50-4185-a7df-99210d3a9561	true	introspection.token.claim
88bdbc9c-4d50-4185-a7df-99210d3a9561	true	userinfo.token.claim
88bdbc9c-4d50-4185-a7df-99210d3a9561	username	user.attribute
88bdbc9c-4d50-4185-a7df-99210d3a9561	true	id.token.claim
88bdbc9c-4d50-4185-a7df-99210d3a9561	true	access.token.claim
88bdbc9c-4d50-4185-a7df-99210d3a9561	preferred_username	claim.name
88bdbc9c-4d50-4185-a7df-99210d3a9561	String	jsonType.label
cf7d7139-2a5b-4552-9876-638e1fe544fa	true	introspection.token.claim
cf7d7139-2a5b-4552-9876-638e1fe544fa	true	userinfo.token.claim
cf7d7139-2a5b-4552-9876-638e1fe544fa	lastName	user.attribute
cf7d7139-2a5b-4552-9876-638e1fe544fa	true	id.token.claim
cf7d7139-2a5b-4552-9876-638e1fe544fa	true	access.token.claim
cf7d7139-2a5b-4552-9876-638e1fe544fa	family_name	claim.name
cf7d7139-2a5b-4552-9876-638e1fe544fa	String	jsonType.label
d01af460-c4ac-43f2-b357-78680fe3b01c	true	introspection.token.claim
d01af460-c4ac-43f2-b357-78680fe3b01c	true	userinfo.token.claim
d01af460-c4ac-43f2-b357-78680fe3b01c	website	user.attribute
d01af460-c4ac-43f2-b357-78680fe3b01c	true	id.token.claim
d01af460-c4ac-43f2-b357-78680fe3b01c	true	access.token.claim
d01af460-c4ac-43f2-b357-78680fe3b01c	website	claim.name
d01af460-c4ac-43f2-b357-78680fe3b01c	String	jsonType.label
d351b1b5-7a7c-4ead-ac1a-4f0a387d42d3	true	introspection.token.claim
d351b1b5-7a7c-4ead-ac1a-4f0a387d42d3	true	userinfo.token.claim
d351b1b5-7a7c-4ead-ac1a-4f0a387d42d3	true	id.token.claim
d351b1b5-7a7c-4ead-ac1a-4f0a387d42d3	true	access.token.claim
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	true	introspection.token.claim
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	true	userinfo.token.claim
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	updatedAt	user.attribute
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	true	id.token.claim
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	true	access.token.claim
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	updated_at	claim.name
dcc8bd7d-3a3a-4244-a09b-94b9ea5940f1	long	jsonType.label
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	true	introspection.token.claim
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	true	userinfo.token.claim
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	zoneinfo	user.attribute
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	true	id.token.claim
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	true	access.token.claim
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	zoneinfo	claim.name
dd8afbca-f1d9-46bf-b8b6-8497ca079dab	String	jsonType.label
f099d32a-3fe8-4896-a060-90ecf339c679	true	introspection.token.claim
f099d32a-3fe8-4896-a060-90ecf339c679	true	userinfo.token.claim
f099d32a-3fe8-4896-a060-90ecf339c679	locale	user.attribute
f099d32a-3fe8-4896-a060-90ecf339c679	true	id.token.claim
f099d32a-3fe8-4896-a060-90ecf339c679	true	access.token.claim
f099d32a-3fe8-4896-a060-90ecf339c679	locale	claim.name
f099d32a-3fe8-4896-a060-90ecf339c679	String	jsonType.label
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	true	introspection.token.claim
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	true	userinfo.token.claim
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	email	user.attribute
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	true	id.token.claim
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	true	access.token.claim
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	email	claim.name
489cdf67-1827-4d74-bf8a-7c1d9f0cfd68	String	jsonType.label
f34bd7d9-5dd8-4e31-8292-7811ad108cff	true	introspection.token.claim
f34bd7d9-5dd8-4e31-8292-7811ad108cff	true	userinfo.token.claim
f34bd7d9-5dd8-4e31-8292-7811ad108cff	emailVerified	user.attribute
f34bd7d9-5dd8-4e31-8292-7811ad108cff	true	id.token.claim
f34bd7d9-5dd8-4e31-8292-7811ad108cff	true	access.token.claim
f34bd7d9-5dd8-4e31-8292-7811ad108cff	email_verified	claim.name
f34bd7d9-5dd8-4e31-8292-7811ad108cff	boolean	jsonType.label
688762ae-7775-4a32-9fd6-5966f7a81d94	formatted	user.attribute.formatted
688762ae-7775-4a32-9fd6-5966f7a81d94	country	user.attribute.country
688762ae-7775-4a32-9fd6-5966f7a81d94	true	introspection.token.claim
688762ae-7775-4a32-9fd6-5966f7a81d94	postal_code	user.attribute.postal_code
688762ae-7775-4a32-9fd6-5966f7a81d94	true	userinfo.token.claim
688762ae-7775-4a32-9fd6-5966f7a81d94	street	user.attribute.street
688762ae-7775-4a32-9fd6-5966f7a81d94	true	id.token.claim
688762ae-7775-4a32-9fd6-5966f7a81d94	region	user.attribute.region
688762ae-7775-4a32-9fd6-5966f7a81d94	true	access.token.claim
688762ae-7775-4a32-9fd6-5966f7a81d94	locality	user.attribute.locality
54435d1e-47dd-458a-8809-84fbc6aefa31	true	introspection.token.claim
54435d1e-47dd-458a-8809-84fbc6aefa31	true	userinfo.token.claim
54435d1e-47dd-458a-8809-84fbc6aefa31	phoneNumberVerified	user.attribute
54435d1e-47dd-458a-8809-84fbc6aefa31	true	id.token.claim
54435d1e-47dd-458a-8809-84fbc6aefa31	true	access.token.claim
54435d1e-47dd-458a-8809-84fbc6aefa31	phone_number_verified	claim.name
54435d1e-47dd-458a-8809-84fbc6aefa31	boolean	jsonType.label
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	true	introspection.token.claim
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	true	userinfo.token.claim
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	phoneNumber	user.attribute
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	true	id.token.claim
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	true	access.token.claim
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	phone_number	claim.name
7fe8d6ba-6aeb-47c0-a7b2-0197d8f96397	String	jsonType.label
018b1d6f-9ceb-4d63-a252-345500adb858	true	introspection.token.claim
018b1d6f-9ceb-4d63-a252-345500adb858	true	multivalued
018b1d6f-9ceb-4d63-a252-345500adb858	foo	user.attribute
018b1d6f-9ceb-4d63-a252-345500adb858	true	access.token.claim
018b1d6f-9ceb-4d63-a252-345500adb858	realm_access.roles	claim.name
018b1d6f-9ceb-4d63-a252-345500adb858	String	jsonType.label
cd1471f4-2cd2-482d-8e81-3f95873d142e	true	introspection.token.claim
cd1471f4-2cd2-482d-8e81-3f95873d142e	true	multivalued
cd1471f4-2cd2-482d-8e81-3f95873d142e	foo	user.attribute
cd1471f4-2cd2-482d-8e81-3f95873d142e	true	access.token.claim
cd1471f4-2cd2-482d-8e81-3f95873d142e	resource_access.${client_id}.roles	claim.name
cd1471f4-2cd2-482d-8e81-3f95873d142e	String	jsonType.label
da931420-1529-4088-b697-5453382b2a8b	true	introspection.token.claim
da931420-1529-4088-b697-5453382b2a8b	true	access.token.claim
c6e65862-ba9f-4524-8ac4-3da6d1ceaf1c	true	introspection.token.claim
c6e65862-ba9f-4524-8ac4-3da6d1ceaf1c	true	access.token.claim
264f9582-e4e0-4037-801b-ea7f426459f7	true	introspection.token.claim
264f9582-e4e0-4037-801b-ea7f426459f7	true	multivalued
264f9582-e4e0-4037-801b-ea7f426459f7	foo	user.attribute
264f9582-e4e0-4037-801b-ea7f426459f7	true	id.token.claim
264f9582-e4e0-4037-801b-ea7f426459f7	true	access.token.claim
264f9582-e4e0-4037-801b-ea7f426459f7	groups	claim.name
264f9582-e4e0-4037-801b-ea7f426459f7	String	jsonType.label
b77711ff-0371-43f1-86de-9c47fcc7e99d	true	introspection.token.claim
b77711ff-0371-43f1-86de-9c47fcc7e99d	true	userinfo.token.claim
b77711ff-0371-43f1-86de-9c47fcc7e99d	username	user.attribute
b77711ff-0371-43f1-86de-9c47fcc7e99d	true	id.token.claim
b77711ff-0371-43f1-86de-9c47fcc7e99d	true	access.token.claim
b77711ff-0371-43f1-86de-9c47fcc7e99d	upn	claim.name
b77711ff-0371-43f1-86de-9c47fcc7e99d	String	jsonType.label
61814488-3e53-4739-8594-c6f16b2ab241	true	introspection.token.claim
61814488-3e53-4739-8594-c6f16b2ab241	true	id.token.claim
61814488-3e53-4739-8594-c6f16b2ab241	true	access.token.claim
0c382a53-d069-4148-8aa2-499c6daaf670	true	introspection.token.claim
0c382a53-d069-4148-8aa2-499c6daaf670	true	access.token.claim
8760d061-a719-471c-b1b1-6a620e1d7b54	AUTH_TIME	user.session.note
8760d061-a719-471c-b1b1-6a620e1d7b54	true	introspection.token.claim
8760d061-a719-471c-b1b1-6a620e1d7b54	true	id.token.claim
8760d061-a719-471c-b1b1-6a620e1d7b54	true	access.token.claim
8760d061-a719-471c-b1b1-6a620e1d7b54	auth_time	claim.name
8760d061-a719-471c-b1b1-6a620e1d7b54	long	jsonType.label
47209101-b6b3-445e-8034-59dd1d209f53	clientHost	user.session.note
47209101-b6b3-445e-8034-59dd1d209f53	true	introspection.token.claim
47209101-b6b3-445e-8034-59dd1d209f53	true	id.token.claim
47209101-b6b3-445e-8034-59dd1d209f53	true	access.token.claim
47209101-b6b3-445e-8034-59dd1d209f53	clientHost	claim.name
47209101-b6b3-445e-8034-59dd1d209f53	String	jsonType.label
4fba9865-c6c4-45b3-af44-19115053461c	client_id	user.session.note
4fba9865-c6c4-45b3-af44-19115053461c	true	introspection.token.claim
4fba9865-c6c4-45b3-af44-19115053461c	true	id.token.claim
4fba9865-c6c4-45b3-af44-19115053461c	true	access.token.claim
4fba9865-c6c4-45b3-af44-19115053461c	client_id	claim.name
4fba9865-c6c4-45b3-af44-19115053461c	String	jsonType.label
a5715743-7e51-4332-926e-02677799bdf4	clientAddress	user.session.note
a5715743-7e51-4332-926e-02677799bdf4	true	introspection.token.claim
a5715743-7e51-4332-926e-02677799bdf4	true	id.token.claim
a5715743-7e51-4332-926e-02677799bdf4	true	access.token.claim
a5715743-7e51-4332-926e-02677799bdf4	clientAddress	claim.name
a5715743-7e51-4332-926e-02677799bdf4	String	jsonType.label
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	true	introspection.token.claim
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	true	multivalued
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	true	id.token.claim
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	true	access.token.claim
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	organization	claim.name
a96ee526-96e5-4d1f-9bb4-6e3f9b69c5eb	String	jsonType.label
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	true	introspection.token.claim
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	true	userinfo.token.claim
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	locale	user.attribute
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	true	id.token.claim
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	true	access.token.claim
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	locale	claim.name
d2f3dfcb-ace2-47f7-a88c-5cf11bffa6c4	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
973d96fb-e7bb-493a-b27f-0020b0da1731	60	300	60				t	f	0		master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	a4965dcb-4c1d-4086-a24e-69d6357f614b	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	a479d186-b6e0-4b5e-a4ae-17ca9299e441	60221bbd-51be-434f-a445-989857bd0d37	280c5b66-d67d-4369-8fa8-130e7ac2bfa5	65760d3a-41d2-4746-b23a-8ee704a0fdc1	9ad5afc2-6cba-44eb-a270-9270ecb49844	2592000	f	900	t	f	d322e97b-0cd3-4120-845f-69d22d19c92c	0	f	0	0	776b2711-9757-468e-ba12-bd367256a8c8
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	60	300	300			pousada	t	f	0	pousada	quinta-ypua	0	\N	f	f	t	f	EXTERNAL	1800	36000	f	f	fb41c7be-5f4f-4681-88f8-71ce00bc5e21	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	c450e768-7447-496c-8e82-605af43362d4	2d4ddf33-0f2d-4717-8d7e-ada05a639e15	557a4272-6efa-4fa9-a513-810d3a9c085f	9e0f095d-ab1b-4797-a52c-ec4f4cf6e4cf	9e855670-895d-4717-8334-bca4b789cb71	2592000	f	900	t	f	5a3f71e0-8e4c-45be-a3a6-e7b9e59ab244	0	f	0	0	40ba6aab-743b-4a42-88be-64c606013207
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
bruteForceProtected	973d96fb-e7bb-493a-b27f-0020b0da1731	false
permanentLockout	973d96fb-e7bb-493a-b27f-0020b0da1731	false
maxTemporaryLockouts	973d96fb-e7bb-493a-b27f-0020b0da1731	0
bruteForceStrategy	973d96fb-e7bb-493a-b27f-0020b0da1731	MULTIPLE
maxFailureWaitSeconds	973d96fb-e7bb-493a-b27f-0020b0da1731	900
minimumQuickLoginWaitSeconds	973d96fb-e7bb-493a-b27f-0020b0da1731	60
waitIncrementSeconds	973d96fb-e7bb-493a-b27f-0020b0da1731	60
quickLoginCheckMilliSeconds	973d96fb-e7bb-493a-b27f-0020b0da1731	1000
maxDeltaTimeSeconds	973d96fb-e7bb-493a-b27f-0020b0da1731	43200
failureFactor	973d96fb-e7bb-493a-b27f-0020b0da1731	30
realmReusableOtpCode	973d96fb-e7bb-493a-b27f-0020b0da1731	false
firstBrokerLoginFlowId	973d96fb-e7bb-493a-b27f-0020b0da1731	1c8acaea-694c-40e9-a8eb-dbba0dd402bc
displayName	973d96fb-e7bb-493a-b27f-0020b0da1731	Keycloak
displayNameHtml	973d96fb-e7bb-493a-b27f-0020b0da1731	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	973d96fb-e7bb-493a-b27f-0020b0da1731	RS256
offlineSessionMaxLifespanEnabled	973d96fb-e7bb-493a-b27f-0020b0da1731	false
offlineSessionMaxLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	5184000
bruteForceProtected	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
permanentLockout	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
maxTemporaryLockouts	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
bruteForceStrategy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	MULTIPLE
maxFailureWaitSeconds	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	900
minimumQuickLoginWaitSeconds	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	60
waitIncrementSeconds	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	60
quickLoginCheckMilliSeconds	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	1000
maxDeltaTimeSeconds	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	43200
failureFactor	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	30
realmReusableOtpCode	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
defaultSignatureAlgorithm	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	RS256
offlineSessionMaxLifespanEnabled	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
offlineSessionMaxLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	5184000
actionTokenGeneratedByAdminLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	43200
actionTokenGeneratedByUserLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	300
oauth2DeviceCodeLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	600
oauth2DevicePollingInterval	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	5
webAuthnPolicyRpEntityName	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	keycloak
webAuthnPolicySignatureAlgorithms	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ES256,RS256
webAuthnPolicyRpId	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	
webAuthnPolicyAttestationConveyancePreference	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyAuthenticatorAttachment	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyRequireResidentKey	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyUserVerificationRequirement	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyCreateTimeout	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
webAuthnPolicyAvoidSameAuthenticatorRegister	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
webAuthnPolicyRpEntityNamePasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	ES256,RS256
webAuthnPolicyRpIdPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	
webAuthnPolicyAttestationConveyancePreferencePasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyRequireResidentKeyPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	not specified
webAuthnPolicyCreateTimeoutPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
cibaBackchannelTokenDeliveryMode	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	poll
cibaExpiresIn	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	120
cibaInterval	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	5
cibaAuthRequestedUserHint	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	login_hint
parRequestUriLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	60
firstBrokerLoginFlowId	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	8b0316a7-7c51-4276-8a55-50018c818a1e
darkMode	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	true
organizationsEnabled	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
adminPermissionsEnabled	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
verifiableCredentialsEnabled	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	false
clientSessionIdleTimeout	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
clientSessionMaxLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
clientOfflineSessionIdleTimeout	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
clientOfflineSessionMaxLifespan	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	0
client-policies.profiles	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	{"profiles":[]}
client-policies.policies	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	{"policies":[]}
_browser_header.contentSecurityPolicyReportOnly	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	
_browser_header.xContentTypeOptions	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	nosniff
_browser_header.referrerPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	no-referrer
_browser_header.xRobotsTag	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	none
_browser_header.xFrameOptions	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	SAMEORIGIN
_browser_header.contentSecurityPolicy	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	1; mode=block
_browser_header.strictTransportSecurity	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	max-age=31536000; includeSubDomains
darkMode	973d96fb-e7bb-493a-b27f-0020b0da1731	true
cibaBackchannelTokenDeliveryMode	973d96fb-e7bb-493a-b27f-0020b0da1731	poll
cibaExpiresIn	973d96fb-e7bb-493a-b27f-0020b0da1731	120
cibaAuthRequestedUserHint	973d96fb-e7bb-493a-b27f-0020b0da1731	login_hint
parRequestUriLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	60
cibaInterval	973d96fb-e7bb-493a-b27f-0020b0da1731	5
organizationsEnabled	973d96fb-e7bb-493a-b27f-0020b0da1731	false
adminPermissionsEnabled	973d96fb-e7bb-493a-b27f-0020b0da1731	false
verifiableCredentialsEnabled	973d96fb-e7bb-493a-b27f-0020b0da1731	false
actionTokenGeneratedByAdminLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	43200
actionTokenGeneratedByUserLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	300
webAuthnPolicyRpEntityName	973d96fb-e7bb-493a-b27f-0020b0da1731	keycloak
webAuthnPolicySignatureAlgorithms	973d96fb-e7bb-493a-b27f-0020b0da1731	ES256,RS256
webAuthnPolicyRpId	973d96fb-e7bb-493a-b27f-0020b0da1731	
webAuthnPolicyAttestationConveyancePreference	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyAuthenticatorAttachment	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyRequireResidentKey	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyUserVerificationRequirement	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyCreateTimeout	973d96fb-e7bb-493a-b27f-0020b0da1731	0
webAuthnPolicyAvoidSameAuthenticatorRegister	973d96fb-e7bb-493a-b27f-0020b0da1731	false
webAuthnPolicyRpEntityNamePasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	ES256,RS256
webAuthnPolicyRpIdPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	
webAuthnPolicyAttestationConveyancePreferencePasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyRequireResidentKeyPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	not specified
webAuthnPolicyCreateTimeoutPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	973d96fb-e7bb-493a-b27f-0020b0da1731	false
client-policies.profiles	973d96fb-e7bb-493a-b27f-0020b0da1731	{"profiles":[]}
client-policies.policies	973d96fb-e7bb-493a-b27f-0020b0da1731	{"policies":[]}
oauth2DeviceCodeLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	600
oauth2DevicePollingInterval	973d96fb-e7bb-493a-b27f-0020b0da1731	5
clientSessionIdleTimeout	973d96fb-e7bb-493a-b27f-0020b0da1731	0
clientSessionMaxLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	0
clientOfflineSessionIdleTimeout	973d96fb-e7bb-493a-b27f-0020b0da1731	0
clientOfflineSessionMaxLifespan	973d96fb-e7bb-493a-b27f-0020b0da1731	0
_browser_header.contentSecurityPolicyReportOnly	973d96fb-e7bb-493a-b27f-0020b0da1731	
_browser_header.xContentTypeOptions	973d96fb-e7bb-493a-b27f-0020b0da1731	nosniff
_browser_header.referrerPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	no-referrer
_browser_header.xRobotsTag	973d96fb-e7bb-493a-b27f-0020b0da1731	none
_browser_header.xFrameOptions	973d96fb-e7bb-493a-b27f-0020b0da1731	SAMEORIGIN
_browser_header.contentSecurityPolicy	973d96fb-e7bb-493a-b27f-0020b0da1731	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	973d96fb-e7bb-493a-b27f-0020b0da1731	1; mode=block
_browser_header.strictTransportSecurity	973d96fb-e7bb-493a-b27f-0020b0da1731	max-age=31536000; includeSubDomains
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	jboss-logging
973d96fb-e7bb-493a-b27f-0020b0da1731	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	973d96fb-e7bb-493a-b27f-0020b0da1731
password	password	t	t	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.redirect_uris (client_id, value) FROM stdin;
45110c87-41e9-4cf3-b4ed-958db7b94b24	/realms/master/account/*
f3b08e34-80ce-405d-b6e7-0c35eda81170	/realms/master/account/*
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	/admin/master/console/*
069afb20-1212-44a1-bd8b-4ed84b6e9003	/realms/quinta-ypua/account/*
9ce4bbb9-10bf-4b13-8356-225e00c27007	/realms/quinta-ypua/account/*
bfcaa195-cce4-416f-8626-f0a3b93e381b	/admin/quinta-ypua/console/*
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	*
2c9ea497-af7f-4ac2-a6bb-831391b778f6	/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
6c418620-0f89-49ca-8ee7-beea81ef4ea4	VERIFY_EMAIL	Verify Email	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	VERIFY_EMAIL	50
3462391d-8c06-4765-97cd-08d4ff0f5925	UPDATE_PROFILE	Update Profile	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	UPDATE_PROFILE	40
7cce5e87-f08a-4cfa-aa8e-6e1675b45cdc	CONFIGURE_TOTP	Configure OTP	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	CONFIGURE_TOTP	10
3a9f56d9-a185-4980-98f9-ad0bfdb4ee4b	UPDATE_PASSWORD	Update Password	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	UPDATE_PASSWORD	30
e519782d-281d-4935-81cf-16b459bf2635	TERMS_AND_CONDITIONS	Terms and Conditions	973d96fb-e7bb-493a-b27f-0020b0da1731	f	f	TERMS_AND_CONDITIONS	20
c7b5a9e8-48a0-44f9-a0ca-0077a8f8bc8c	delete_account	Delete Account	973d96fb-e7bb-493a-b27f-0020b0da1731	f	f	delete_account	60
97673689-864e-4612-b3fe-8e8715a1c41e	delete_credential	Delete Credential	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	delete_credential	100
ed55c378-9102-4ccb-ac22-306742a9d7ae	update_user_locale	Update User Locale	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	update_user_locale	1000
55f7954f-4b22-466b-bccd-fb6673c46337	webauthn-register	Webauthn Register	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	webauthn-register	70
126c7707-3a8c-4982-87f7-b4920296cdee	webauthn-register-passwordless	Webauthn Register Passwordless	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	webauthn-register-passwordless	80
59b7a8f6-2e83-4394-8e18-e9c25fa82b85	VERIFY_PROFILE	Verify Profile	973d96fb-e7bb-493a-b27f-0020b0da1731	t	f	VERIFY_PROFILE	90
299c5112-5d58-4552-8f84-117290e251e5	VERIFY_EMAIL	Verify Email	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	VERIFY_EMAIL	50
cd7a13cd-8c8d-42e3-aa23-e8002be1e092	UPDATE_PROFILE	Update Profile	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	UPDATE_PROFILE	40
a22e13f2-a465-4802-b522-1fd5c6f20a09	CONFIGURE_TOTP	Configure OTP	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	CONFIGURE_TOTP	10
4bee5899-9204-445b-855d-b575443e0db1	UPDATE_PASSWORD	Update Password	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	UPDATE_PASSWORD	30
c0a0b2b1-8be8-4d83-8d40-473f3d5561e8	TERMS_AND_CONDITIONS	Terms and Conditions	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f	f	TERMS_AND_CONDITIONS	20
c41761cf-9e66-4129-85b2-4f4bab8802a0	delete_account	Delete Account	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	f	f	delete_account	60
e0cf5f8a-6aa4-4b44-9a4a-da3c5daa3cfb	delete_credential	Delete Credential	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	delete_credential	100
eda1a146-d532-4004-93ce-0caea220f7dc	update_user_locale	Update User Locale	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	update_user_locale	1000
79739b70-beb8-44c2-b918-8db850b842ed	webauthn-register	Webauthn Register	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	webauthn-register	70
c31aea0e-7b8f-4cda-ada7-f11a5ceffdbf	webauthn-register-passwordless	Webauthn Register Passwordless	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	webauthn-register-passwordless	80
891d5b5f-ab49-4b14-bb3f-0c8c935425b8	VERIFY_PROFILE	Verify Profile	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	t	f	VERIFY_PROFILE	90
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: revoked_token; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.revoked_token (id, expire) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
f3b08e34-80ce-405d-b6e7-0c35eda81170	f38781bf-4267-499e-bdac-91df42e62300
f3b08e34-80ce-405d-b6e7-0c35eda81170	624216f9-9ba9-4813-bb4f-2fdd008b5645
9ce4bbb9-10bf-4b13-8356-225e00c27007	f18906f5-9b55-4ba8-b2c7-6a4b98c56049
9ce4bbb9-10bf-4b13-8356-225e00c27007	78c70b68-43b3-41b9-bb50-1631aa1a3cf2
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
03d9df00-e0f0-4252-92f3-fbe863910716	\N	32f38a06-336f-445b-a513-2d016bebe394	t	t	\N	\N	\N	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	admin	1773101124202	\N	0
38551cec-aa01-4483-a9e3-18bb0ca362cc	murilo.vieracruz@gmail.com	murilo.vieracruz@gmail.com	t	t	\N	Murilo	Vieira	ae51368a-3c1c-481e-a9cf-2b3d9c76fc43	murilo	1774808616270	\N	0
5727be85-b2de-4841-9e4f-4381021fa33e	\N	027d472e-f128-41f2-aacc-75cf976ea7d6	t	t	\N	\N	\N	973d96fb-e7bb-493a-b27f-0020b0da1731	admin	1778451725116	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_group_membership (group_id, user_id, membership_type) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
40ba6aab-743b-4a42-88be-64c606013207	03d9df00-e0f0-4252-92f3-fbe863910716
5b37f676-c63d-4f38-bd8f-c46a0b9e9577	03d9df00-e0f0-4252-92f3-fbe863910716
b1bf9883-5115-4bdb-8dfe-ae85841fbbd1	03d9df00-e0f0-4252-92f3-fbe863910716
aee34c51-3a24-495d-aabb-09d829552ede	03d9df00-e0f0-4252-92f3-fbe863910716
ac694953-e165-443e-8716-06860a9a7b24	03d9df00-e0f0-4252-92f3-fbe863910716
bfc43dc6-920b-46c1-a1c1-a11f5be1022d	03d9df00-e0f0-4252-92f3-fbe863910716
f03e60ff-d5e0-4a45-a077-c5ffaa6acaf9	03d9df00-e0f0-4252-92f3-fbe863910716
8b61eb95-be8d-4bfd-95e1-4600c0978efd	03d9df00-e0f0-4252-92f3-fbe863910716
2aef1606-6d21-4edb-a516-7da893bbea9d	03d9df00-e0f0-4252-92f3-fbe863910716
46cf0d9b-6775-4bfa-9e7d-a6addfed51ea	03d9df00-e0f0-4252-92f3-fbe863910716
1ac05c5c-2b14-4df0-ba34-cae4148f86c8	03d9df00-e0f0-4252-92f3-fbe863910716
5b8650bf-8ad3-4bc4-b6e6-e499e0f394ef	03d9df00-e0f0-4252-92f3-fbe863910716
89ff538a-8fcd-4a99-a186-0fd5218c1df2	03d9df00-e0f0-4252-92f3-fbe863910716
45c183f9-cddb-4ffc-83f6-ebfbef307480	03d9df00-e0f0-4252-92f3-fbe863910716
334b38e6-7e76-4529-babc-4f5b9d070041	03d9df00-e0f0-4252-92f3-fbe863910716
8f9f1a36-6669-4e38-b6b3-3a63756dc855	03d9df00-e0f0-4252-92f3-fbe863910716
bd2b7a75-fdcd-4ebb-9976-a684f92b6445	03d9df00-e0f0-4252-92f3-fbe863910716
d8079922-fd5b-4872-8c09-1d0fe58e5c56	03d9df00-e0f0-4252-92f3-fbe863910716
0cd820a1-99f7-4803-b108-c65c31283370	03d9df00-e0f0-4252-92f3-fbe863910716
2dfb0f70-a2c3-46d1-9a53-9c5d00138a92	03d9df00-e0f0-4252-92f3-fbe863910716
b7bf805d-1e2d-48bb-9669-8d51bde19f79	03d9df00-e0f0-4252-92f3-fbe863910716
7758a27e-9422-458e-b537-7fb08a87acce	03d9df00-e0f0-4252-92f3-fbe863910716
1c427514-2ed9-484f-862f-1eee7968e512	03d9df00-e0f0-4252-92f3-fbe863910716
45430fde-c450-4a8f-91d6-2321b78548c3	03d9df00-e0f0-4252-92f3-fbe863910716
f18906f5-9b55-4ba8-b2c7-6a4b98c56049	03d9df00-e0f0-4252-92f3-fbe863910716
f8eec290-372a-4c51-bb3e-977e144384f6	03d9df00-e0f0-4252-92f3-fbe863910716
18e65ae0-68e4-441a-9858-453bd98af48c	03d9df00-e0f0-4252-92f3-fbe863910716
698d02ba-6e2b-4abd-9d9c-04b96c946e92	03d9df00-e0f0-4252-92f3-fbe863910716
78c70b68-43b3-41b9-bb50-1631aa1a3cf2	03d9df00-e0f0-4252-92f3-fbe863910716
40ba6aab-743b-4a42-88be-64c606013207	38551cec-aa01-4483-a9e3-18bb0ca362cc
1be57665-c03c-4a6b-9b4f-fb7a58afb22c	38551cec-aa01-4483-a9e3-18bb0ca362cc
0f83a87b-4cea-439d-bcce-82ffda859348	38551cec-aa01-4483-a9e3-18bb0ca362cc
28a1c92c-285c-45b9-9979-b203371cef94	38551cec-aa01-4483-a9e3-18bb0ca362cc
8f49165f-3d69-4978-b4bc-e4b99267c2f9	38551cec-aa01-4483-a9e3-18bb0ca362cc
1689a33c-7964-41d2-abac-fe9ae69e18b1	38551cec-aa01-4483-a9e3-18bb0ca362cc
30fec081-b3ba-4227-9cb9-5468bb7f6c3c	38551cec-aa01-4483-a9e3-18bb0ca362cc
20dff205-6580-416a-8c6d-00775bccab09	38551cec-aa01-4483-a9e3-18bb0ca362cc
148597b7-2ee7-4a3b-958a-e229f3e85480	38551cec-aa01-4483-a9e3-18bb0ca362cc
3363b1d2-f88a-4bf0-8a91-21ad758244fd	38551cec-aa01-4483-a9e3-18bb0ca362cc
acf45de6-c155-40f6-aae6-88f3795dd996	38551cec-aa01-4483-a9e3-18bb0ca362cc
5bb3511e-1152-4201-9c7d-b9ebb8be5dc4	38551cec-aa01-4483-a9e3-18bb0ca362cc
a1478a9a-4c46-4fb6-b2e9-e9029c3cd48e	38551cec-aa01-4483-a9e3-18bb0ca362cc
776b2711-9757-468e-ba12-bd367256a8c8	5727be85-b2de-4841-9e4f-4381021fa33e
31d48bd4-6bd9-48fc-ad9b-80039775b22c	5727be85-b2de-4841-9e4f-4381021fa33e
f6753958-1377-446b-86f9-36ac4f21a0b8	5727be85-b2de-4841-9e4f-4381021fa33e
81e028c1-02df-410a-a4fd-616c5edbcd09	5727be85-b2de-4841-9e4f-4381021fa33e
76cb6387-3e91-431a-a856-b6fb4e4701bd	5727be85-b2de-4841-9e4f-4381021fa33e
69dff334-6f22-4793-a201-f0ed5d8132be	5727be85-b2de-4841-9e4f-4381021fa33e
e5b48493-d112-4aef-9e81-33489c01c97f	5727be85-b2de-4841-9e4f-4381021fa33e
6c8a40bb-6f5d-4167-a4dd-3fdfaee65203	5727be85-b2de-4841-9e4f-4381021fa33e
0ef24ca6-2eb2-42ff-bc6e-1effdf3d37bc	5727be85-b2de-4841-9e4f-4381021fa33e
9eb455d4-30d1-40d0-acec-1fe09fc4bdfb	5727be85-b2de-4841-9e4f-4381021fa33e
627cc7b2-f228-4eb2-8893-e2e445d3bb14	5727be85-b2de-4841-9e4f-4381021fa33e
b1326db7-413f-4021-9413-78c1c6a0d5d0	5727be85-b2de-4841-9e4f-4381021fa33e
4447ed1d-d58d-4ed9-ad86-6f71cc921da5	5727be85-b2de-4841-9e4f-4381021fa33e
3723b752-9dc2-4172-afb3-1135dffb945c	5727be85-b2de-4841-9e4f-4381021fa33e
e1bcc396-4bc3-4326-a75a-edc59c25778d	5727be85-b2de-4841-9e4f-4381021fa33e
8f18532c-0900-413b-bed3-21719a56cabb	5727be85-b2de-4841-9e4f-4381021fa33e
09d67c9e-d80b-418e-99ed-d62d074e86cc	5727be85-b2de-4841-9e4f-4381021fa33e
7bb41f93-f2fc-4460-aea4-f81e8b8d248f	5727be85-b2de-4841-9e4f-4381021fa33e
073fa9d6-8a69-4a3b-aede-107ee59e9df4	5727be85-b2de-4841-9e4f-4381021fa33e
6e389bb3-ce7c-4692-b5b2-07b3c7dc8f12	5727be85-b2de-4841-9e4f-4381021fa33e
30429526-955a-4dd7-a60d-2d1fdc4348c0	5727be85-b2de-4841-9e4f-4381021fa33e
4ae7184c-3341-42cd-a265-0fd28ce5d529	5727be85-b2de-4841-9e4f-4381021fa33e
8c0eb1a0-53bb-4cd0-97b2-adf578e601f4	5727be85-b2de-4841-9e4f-4381021fa33e
476f4b98-4be1-4457-8306-9e0d6148f666	5727be85-b2de-4841-9e4f-4381021fa33e
b500f8ab-1382-47e3-b4c2-2805d15246a4	5727be85-b2de-4841-9e4f-4381021fa33e
95a36a78-8686-4528-a66e-9d0fac949f09	5727be85-b2de-4841-9e4f-4381021fa33e
609716fd-5ee6-4075-bb03-e9ff43c15ac7	5727be85-b2de-4841-9e4f-4381021fa33e
554ece2a-9743-40fa-9f1a-c021a155e946	5727be85-b2de-4841-9e4f-4381021fa33e
374fcf09-a28e-46fe-83fd-c39da332212a	5727be85-b2de-4841-9e4f-4381021fa33e
3abeddab-8013-41f3-9755-0fe3f41da551	5727be85-b2de-4841-9e4f-4381021fa33e
f3b5540b-db08-4268-ad8a-8801a11db79d	5727be85-b2de-4841-9e4f-4381021fa33e
82539967-8892-455c-9568-ee4310c734bf	5727be85-b2de-4841-9e4f-4381021fa33e
3e95950d-949b-448b-8348-28b1afedc383	5727be85-b2de-4841-9e4f-4381021fa33e
bafbf1e0-6924-4625-907d-61bc2dc0fffa	5727be85-b2de-4841-9e4f-4381021fa33e
5648fb7c-f0a8-479b-9d66-234affa29e63	5727be85-b2de-4841-9e4f-4381021fa33e
49a7a532-2417-46b1-bef2-47042cce81ed	5727be85-b2de-4841-9e4f-4381021fa33e
624216f9-9ba9-4813-bb4f-2fdd008b5645	5727be85-b2de-4841-9e4f-4381021fa33e
ff3d2c28-3f6e-4ab3-9311-3c5a5dca16f0	5727be85-b2de-4841-9e4f-4381021fa33e
99fa23b7-e3be-49aa-8773-01ba5451d42f	5727be85-b2de-4841-9e4f-4381021fa33e
f4148dc3-6dbe-44f3-b0fa-62c0f9118660	5727be85-b2de-4841-9e4f-4381021fa33e
943144ac-34b5-4715-9d66-8a3da931b15a	5727be85-b2de-4841-9e4f-4381021fa33e
ac2baf54-c67c-4d84-8588-2f28ebf63e11	5727be85-b2de-4841-9e4f-4381021fa33e
df7f88cf-c4c9-4b77-bb45-204cd05eb173	5727be85-b2de-4841-9e4f-4381021fa33e
f38781bf-4267-499e-bdac-91df42e62300	5727be85-b2de-4841-9e4f-4381021fa33e
c4c40f7b-62eb-4a9a-a55b-63d954fd8ce9	5727be85-b2de-4841-9e4f-4381021fa33e
1ab458ee-e7d8-4871-a5e0-a04cd5ea883d	5727be85-b2de-4841-9e4f-4381021fa33e
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: keycloak
--

COPY public.web_origins (client_id, value) FROM stdin;
d13aa8eb-2614-4053-a25a-bf35f9c9a73d	+
bfcaa195-cce4-416f-8626-f0a3b93e381b	+
2c8a6b1c-a43c-4a0d-b91f-b72c0aca8afc	*
2c9ea497-af7f-4ac2-a6bb-831391b778f6	/*
\.


--
-- Name: org_domain ORG_DOMAIN_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.org_domain
    ADD CONSTRAINT "ORG_DOMAIN_pkey" PRIMARY KEY (id, name);


--
-- Name: org ORG_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT "ORG_pkey" PRIMARY KEY (id);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: jgroups_ping constraint_jgroups_ping; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.jgroups_ping
    ADD CONSTRAINT constraint_jgroups_ping PRIMARY KEY (address);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: revoked_token constraint_rt; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.revoked_token
    ADD CONSTRAINT constraint_rt PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: user_consent uk_external_consent; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_external_consent UNIQUE (client_storage_provider, external_client_id, user_id);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_local_consent; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_local_consent UNIQUE (client_id, user_id);


--
-- Name: org uk_org_alias; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_alias UNIQUE (realm_id, alias);


--
-- Name: org uk_org_group; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_group UNIQUE (group_id);


--
-- Name: org uk_org_name; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_name UNIQUE (realm_id, name);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_att_by_name_value; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_client_att_by_name_value ON public.client_attributes USING btree (name, substr(value, 1, 255));


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_idp_for_login; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_idp_for_login ON public.identity_provider USING btree (realm_id, enabled, link_only, hide_on_login, organization_id);


--
-- Name: idx_idp_realm_org; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_idp_realm_org ON public.identity_provider USING btree (realm_id, organization_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_uss_by_broker_session_id; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_offline_uss_by_broker_session_id ON public.offline_user_session USING btree (broker_session_id, realm_id);


--
-- Name: idx_offline_uss_by_last_session_refresh; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_offline_uss_by_last_session_refresh ON public.offline_user_session USING btree (realm_id, offline_flag, last_session_refresh);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_org_domain_org_id; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_org_domain_org_id ON public.org_domain USING btree (org_id);


--
-- Name: idx_perm_ticket_owner; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_perm_ticket_owner ON public.resource_server_perm_ticket USING btree (owner);


--
-- Name: idx_perm_ticket_requester; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_perm_ticket_requester ON public.resource_server_perm_ticket USING btree (requester);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_rev_token_on_expire; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_rev_token_on_expire ON public.revoked_token USING btree (expire);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_usconsent_scope_id; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_usconsent_scope_id ON public.user_consent_client_scope USING btree (scope_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: user_attr_long_values; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX user_attr_long_values ON public.user_attribute USING btree (long_value_hash, name);


--
-- Name: user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: keycloak
--

CREATE INDEX user_attr_long_values_lower_case ON public.user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: keycloak
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

