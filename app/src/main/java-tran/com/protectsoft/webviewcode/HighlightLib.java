package com.protectsoft.webviewcode;

/**
 *
 * i transferred the highlight.js library here so i can inject the code into the webview
 * so no external resources is needed
 */
 final class HighlightLib {


    final static String HIGHLIGHTJS = "!function(e){var n=\"object\"==typeof window&&window||\"object\"==typeof self&&self;\"undefined\"!=typeof exports?e(exports):n&&(n.hljs=e({}),\"function\"==typeof define&&define.amd&&define([],function(){return n.hljs}))}(function(e){function n(e){return e.replace(/&/gm,\"&amp;\").replace(/</gm,\"&lt;\").replace(/>/gm,\"&gt;\")}function t(e){return e.nodeName.toLowerCase()}function r(e,n){var t=e&&e.exec(n);return t&&0==t.index}function a(e){return/^(no-?highlight|plain|text)$/i.test(e)}function i(e){var n,t,r,i=e.className+\" \";if(i+=e.parentNode?e.parentNode.className:\"\",t=/\\blang(?:uage)?-([\\w-]+)\\b/i.exec(i))return w(t[1])?t[1]:\"no-highlight\";for(i=i.split(/\\s+/),n=0,r=i.length;r>n;n++)if(w(i[n])||a(i[n]))return i[n]}function o(e,n){var t,r={};for(t in e)r[t]=e[t];if(n)for(t in n)r[t]=n[t];return r}function u(e){var n=[];return function r(e,a){for(var i=e.firstChild;i;i=i.nextSibling)3==i.nodeType?a+=i.nodeValue.length:1==i.nodeType&&(n.push({event:\"start\",offset:a,node:i}),a=r(i,a),t(i).match(/br|hr|img|input/)||n.push({event:\"stop\",offset:a,node:i}));return a}(e,0),n}function c(e,r,a){function i(){return e.length&&r.length?e[0].offset!=r[0].offset?e[0].offset<r[0].offset?e:r:\"start\"==r[0].event?e:r:e.length?e:r}function o(e){function r(e){return\" \"+e.nodeName+'=\"'+n(e.value)+'\"'}f+=\"<\"+t(e)+Array.prototype.map.call(e.attributes,r).join(\"\")+\">\"}function u(e){f+=\"</\"+t(e)+\">\"}function c(e){(\"start\"==e.event?o:u)(e.node)}for(var s=0,f=\"\",l=[];e.length||r.length;){var g=i();if(f+=n(a.substr(s,g[0].offset-s)),s=g[0].offset,g==e){l.reverse().forEach(u);do c(g.splice(0,1)[0]),g=i();while(g==e&&g.length&&g[0].offset==s);l.reverse().forEach(o)}else\"start\"==g[0].event?l.push(g[0].node):l.pop(),c(g.splice(0,1)[0])}return f+n(a.substr(s))}function s(e){function n(e){return e&&e.source||e}function t(t,r){return new RegExp(n(t),\"m\"+(e.cI?\"i\":\"\")+(r?\"g\":\"\"))}function r(a,i){if(!a.compiled){if(a.compiled=!0,a.k=a.k||a.bK,a.k){var u={},c=function(n,t){e.cI&&(t=t.toLowerCase()),t.split(\" \").forEach(function(e){var t=e.split(\"|\");u[t[0]]=[n,t[1]?Number(t[1]):1]})};\"string\"==typeof a.k?c(\"keyword\",a.k):Object.keys(a.k).forEach(function(e){c(e,a.k[e])}),a.k=u}a.lR=t(a.l||/\\b\\w+\\b/,!0),i&&(a.bK&&(a.b=\"\\\\b(\"+a.bK.split(\" \").join(\"|\")+\")\\\\b\"),a.b||(a.b=/\\B|\\b/),a.bR=t(a.b),a.e||a.eW||(a.e=/\\B|\\b/),a.e&&(a.eR=t(a.e)),a.tE=n(a.e)||\"\",a.eW&&i.tE&&(a.tE+=(a.e?\"|\":\"\")+i.tE)),a.i&&(a.iR=t(a.i)),void 0===a.r&&(a.r=1),a.c||(a.c=[]);var s=[];a.c.forEach(function(e){e.v?e.v.forEach(function(n){s.push(o(e,n))}):s.push(\"self\"==e?a:e)}),a.c=s,a.c.forEach(function(e){r(e,a)}),a.starts&&r(a.starts,i);var f=a.c.map(function(e){return e.bK?\"\\\\.?(\"+e.b+\")\\\\.?\":e.b}).concat([a.tE,a.i]).map(n).filter(Boolean);a.t=f.length?t(f.join(\"|\"),!0):{exec:function(){return null}}}}r(e)}function f(e,t,a,i){function o(e,n){for(var t=0;t<n.c.length;t++)if(r(n.c[t].bR,e))return n.c[t]}function u(e,n){if(r(e.eR,n)){for(;e.endsParent&&e.parent;)e=e.parent;return e}return e.eW?u(e.parent,n):void 0}function c(e,n){return!a&&r(n.iR,e)}function g(e,n){var t=N.cI?n[0].toLowerCase():n[0];return e.k.hasOwnProperty(t)&&e.k[t]}function p(e,n,t,r){var a=r?\"\":E.classPrefix,i='<span class=\"'+a,o=t?\"\":\"</span>\";return i+=e+'\">',i+n+o}function h(){if(!k.k)return n(M);var e=\"\",t=0;k.lR.lastIndex=0;for(var r=k.lR.exec(M);r;){e+=n(M.substr(t,r.index-t));var a=g(k,r);a?(B+=a[1],e+=p(a[0],n(r[0]))):e+=n(r[0]),t=k.lR.lastIndex,r=k.lR.exec(M)}return e+n(M.substr(t))}function d(){var e=\"string\"==typeof k.sL;if(e&&!R[k.sL])return n(M);var t=e?f(k.sL,M,!0,y[k.sL]):l(M,k.sL.length?k.sL:void 0);return k.r>0&&(B+=t.r),e&&(y[k.sL]=t.top),p(t.language,t.value,!1,!0)}function b(){L+=void 0!==k.sL?d():h(),M=\"\"}function v(e,n){L+=e.cN?p(e.cN,\"\",!0):\"\",k=Object.create(e,{parent:{value:k}})}function m(e,n){if(M+=e,void 0===n)return b(),0;var t=o(n,k);if(t)return t.skip?M+=n:(t.eB&&(M+=n),b(),t.rB||t.eB||(M=n)),v(t,n),t.rB?0:n.length;var r=u(k,n);if(r){var a=k;a.skip?M+=n:(a.rE||a.eE||(M+=n),b(),a.eE&&(M=n));do k.cN&&(L+=\"</span>\"),k.skip||(B+=k.r),k=k.parent;while(k!=r.parent);return r.starts&&v(r.starts,\"\"),a.rE?0:n.length}if(c(n,k))throw new Error('Illegal lexeme \"'+n+'\" for mode \"'+(k.cN||\"<unnamed>\")+'\"');return M+=n,n.length||1}var N=w(e);if(!N)throw new Error('Unknown language: \"'+e+'\"');s(N);var x,k=i||N,y={},L=\"\";for(x=k;x!=N;x=x.parent)x.cN&&(L=p(x.cN,\"\",!0)+L);var M=\"\",B=0;try{for(var C,j,I=0;;){if(k.t.lastIndex=I,C=k.t.exec(t),!C)break;j=m(t.substr(I,C.index-I),C[0]),I=C.index+j}for(m(t.substr(I)),x=k;x.parent;x=x.parent)x.cN&&(L+=\"</span>\");return{r:B,value:L,language:e,top:k}}catch(O){if(-1!=O.message.indexOf(\"Illegal\"))return{r:0,value:n(t)};throw O}}function l(e,t){t=t||E.languages||Object.keys(R);var r={r:0,value:n(e)},a=r;return t.forEach(function(n){if(w(n)){var t=f(n,e,!1);t.language=n,t.r>a.r&&(a=t),t.r>r.r&&(a=r,r=t)}}),a.language&&(r.second_best=a),r}function g(e){return E.tabReplace&&(e=e.replace(/^((<[^>]+>|\\t)+)/gm,function(e,n){return n.replace(/\\t/g,E.tabReplace)})),E.useBR&&(e=e.replace(/\\n/g,\"<br>\")),e}function p(e,n,t){var r=n?x[n]:t,a=[e.trim()];return e.match(/\\bhljs\\b/)||a.push(\"hljs\"),-1===e.indexOf(r)&&a.push(r),a.join(\" \").trim()}function h(e){var n=i(e);if(!a(n)){var t;E.useBR?(t=document.createElementNS(\"http://www.w3.org/1999/xhtml\",\"div\"),t.innerHTML=e.innerHTML.replace(/\\n/g,\"\").replace(/<br[ \\/]*>/g,\"\\n\")):t=e;var r=t.textContent,o=n?f(n,r,!0):l(r),s=u(t);if(s.length){var h=document.createElementNS(\"http://www.w3.org/1999/xhtml\",\"div\");h.innerHTML=o.value,o.value=c(s,u(h),r)}o.value=g(o.value),e.innerHTML=o.value,e.className=p(e.className,n,o.language),e.result={language:o.language,re:o.r},o.second_best&&(e.second_best={language:o.second_best.language,re:o.second_best.r})}}function d(e){E=o(E,e)}function b(){if(!b.called){b.called=!0;var e=document.querySelectorAll(\"pre code\");Array.prototype.forEach.call(e,h)}}function v(){addEventListener(\"DOMContentLoaded\",b,!1),addEventListener(\"load\",b,!1)}function m(n,t){var r=R[n]=t(e);r.aliases&&r.aliases.forEach(function(e){x[e]=n})}function N(){return Object.keys(R)}function w(e){return e=(e||\"\").toLowerCase(),R[e]||R[x[e]]}var E={classPrefix:\"hljs-\",tabReplace:null,useBR:!1,languages:void 0},R={},x={};return e.highlight=f,e.highlightAuto=l,e.fixMarkup=g,e.highlightBlock=h,e.configure=d,e.initHighlighting=b,e.initHighlightingOnLoad=v,e.registerLanguage=m,e.listLanguages=N,e.getLanguage=w,e.inherit=o,e.IR=\"[a-zA-Z]\\\\w*\",e.UIR=\"[a-zA-Z_]\\\\w*\",e.NR=\"\\\\b\\\\d+(\\\\.\\\\d+)?\",e.CNR=\"(-?)(\\\\b0[xX][a-fA-F0-9]+|(\\\\b\\\\d+(\\\\.\\\\d*)?|\\\\.\\\\d+)([eE][-+]?\\\\d+)?)\",e.BNR=\"\\\\b(0b[01]+)\",e.RSR=\"!|!=|!==|%|%=|&|&&|&=|\\\\*|\\\\*=|\\\\+|\\\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\\\?|\\\\[|\\\\{|\\\\(|\\\\^|\\\\^=|\\\\||\\\\|=|\\\\|\\\\||~\",e.BE={b:\"\\\\\\\\[\\\\s\\\\S]\",r:0},e.ASM={cN:\"string\",b:\"'\",e:\"'\",i:\"\\\\n\",c:[e.BE]},e.QSM={cN:\"string\",b:'\"',e:'\"',i:\"\\\\n\",c:[e.BE]},e.PWM={b:/\\b(a|an|the|are|I|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|like)\\b/},e.C=function(n,t,r){var a=e.inherit({cN:\"comment\",b:n,e:t,c:[]},r||{});return a.c.push(e.PWM),a.c.push({cN:\"doctag\",b:\"(?:TODO|FIXME|NOTE|BUG|XXX):\",r:0}),a},e.CLCM=e.C(\"//\",\"$\"),e.CBCM=e.C(\"/\\\\*\",\"\\\\*/\"),e.HCM=e.C(\"#\",\"$\"),e.NM={cN:\"number\",b:e.NR,r:0},e.CNM={cN:\"number\",b:e.CNR,r:0},e.BNM={cN:\"number\",b:e.BNR,r:0},e.CSSNM={cN:\"number\",b:e.NR+\"(%|em|ex|ch|rem|vw|vh|vmin|vmax|cm|mm|in|pt|pc|px|deg|grad|rad|turn|s|ms|Hz|kHz|dpi|dpcm|dppx)?\",r:0},e.RM={cN:\"regexp\",b:/\\//,e:/\\/[gimuy]*/,i:/\\n/,c:[e.BE,{b:/\\[/,e:/\\]/,r:0,c:[e.BE]}]},e.TM={cN:\"title\",b:e.IR,r:0},e.UTM={cN:\"title\",b:e.UIR,r:0},e.METHOD_GUARD={b:\"\\\\.\\\\s*\"+e.UIR,r:0},e});hljs.registerLanguage(\"sql\",function(e){var t=e.C(\"--\",\"$\");return{cI:!0,i:/[<>{}*]/,c:[{bK:\"begin end start commit rollback savepoint lock alter create drop rename call delete do handler insert load replace select truncate update set show pragma grant merge describe use explain help declare prepare execute deallocate release unlock purge reset change stop analyze cache flush optimize repair kill install uninstall checksum restore check backup revoke\",e:/;/,eW:!0,k:{keyword:\"abort abs absolute acc acce accep accept access accessed accessible account acos action activate add addtime admin administer advanced advise aes_decrypt aes_encrypt after agent aggregate ali alia alias allocate allow alter always analyze ancillary and any anydata anydataset anyschema anytype apply archive archived archivelog are as asc ascii asin assembly assertion associate asynchronous at atan atn2 attr attri attrib attribu attribut attribute attributes audit authenticated authentication authid authors auto autoallocate autodblink autoextend automatic availability avg backup badfile basicfile before begin beginning benchmark between bfile bfile_base big bigfile bin binary_double binary_float binlog bit_and bit_count bit_length bit_or bit_xor bitmap blob_base block blocksize body both bound buffer_cache buffer_pool build bulk by byte byteordermark bytes cache caching call calling cancel capacity cascade cascaded case cast catalog category ceil ceiling chain change changed char_base char_length character_length characters characterset charindex charset charsetform charsetid check checksum checksum_agg child choose chr chunk class cleanup clear client clob clob_base clone close cluster_id cluster_probability cluster_set clustering coalesce coercibility col collate collation collect colu colum column column_value columns columns_updated comment commit compact compatibility compiled complete composite_limit compound compress compute concat concat_ws concurrent confirm conn connec connect connect_by_iscycle connect_by_isleaf connect_by_root connect_time connection consider consistent constant constraint constraints constructor container content contents context contributors controlfile conv convert convert_tz corr corr_k corr_s corresponding corruption cos cost count count_big counted covar_pop covar_samp cpu_per_call cpu_per_session crc32 create creation critical cross cube cume_dist curdate current current_date current_time current_timestamp current_user cursor curtime customdatum cycle data database databases datafile datafiles datalength date_add date_cache date_format date_sub dateadd datediff datefromparts datename datepart datetime2fromparts day day_to_second dayname dayofmonth dayofweek dayofyear days db_role_change dbtimezone ddl deallocate declare decode decompose decrement decrypt deduplicate def defa defau defaul default defaults deferred defi defin define degrees delayed delegate delete delete_all delimited demand dense_rank depth dequeue des_decrypt des_encrypt des_key_file desc descr descri describ describe descriptor deterministic diagnostics difference dimension direct_load directory disable disable_all disallow disassociate discardfile disconnect diskgroup distinct distinctrow distribute distributed div do document domain dotnet double downgrade drop dumpfile duplicate duration each edition editionable editions element ellipsis else elsif elt empty enable enable_all enclosed encode encoding encrypt end end-exec endian enforced engine engines enqueue enterprise entityescaping eomonth error errors escaped evalname evaluate event eventdata events except exception exceptions exchange exclude excluding execu execut execute exempt exists exit exp expire explain export export_set extended extent external external_1 external_2 externally extract failed failed_login_attempts failover failure far fast feature_set feature_value fetch field fields file file_name_convert filesystem_like_logging final finish first first_value fixed flash_cache flashback floor flush following follows for forall force form forma format found found_rows freelist freelists freepools fresh from from_base64 from_days ftp full function general generated get get_format get_lock getdate getutcdate global global_name globally go goto grant grants greatest group group_concat group_id grouping grouping_id groups gtid_subtract guarantee guard handler hash hashkeys having hea head headi headin heading heap help hex hierarchy high high_priority hosts hour http id ident_current ident_incr ident_seed identified identity idle_time if ifnull ignore iif ilike ilm immediate import in include including increment index indexes indexing indextype indicator indices inet6_aton inet6_ntoa inet_aton inet_ntoa infile initial initialized initially initrans inmemory inner innodb input insert install instance instantiable instr interface interleaved intersect into invalidate invisible is is_free_lock is_ipv4 is_ipv4_compat is_not is_not_null is_used_lock isdate isnull isolation iterate java join json json_exists keep keep_duplicates key keys kill language large last last_day last_insert_id last_value lax lcase lead leading least leaves left len lenght length less level levels library like like2 like4 likec limit lines link list listagg little ln load load_file lob lobs local localtime localtimestamp locate locator lock locked log log10 log2 logfile logfiles logging logical logical_reads_per_call logoff logon logs long loop low low_priority lower lpad lrtrim ltrim main make_set makedate maketime managed management manual map mapping mask master master_pos_wait match matched materialized max maxextents maximize maxinstances maxlen maxlogfiles maxloghistory maxlogmembers maxsize maxtrans md5 measures median medium member memcompress memory merge microsecond mid migration min minextents minimum mining minus minute minvalue missing mod mode model modification modify module monitoring month months mount move movement multiset mutex name name_const names nan national native natural nav nchar nclob nested never new newline next nextval no no_write_to_binlog noarchivelog noaudit nobadfile nocheck nocompress nocopy nocycle nodelay nodiscardfile noentityescaping noguarantee nokeep nologfile nomapping nomaxvalue nominimize nominvalue nomonitoring none noneditionable nonschema noorder nopr nopro noprom nopromp noprompt norely noresetlogs noreverse normal norowdependencies noschemacheck noswitch not nothing notice notrim novalidate now nowait nth_value nullif nulls num numb numbe nvarchar nvarchar2 object ocicoll ocidate ocidatetime ociduration ociinterval ociloblocator ocinumber ociref ocirefcursor ocirowid ocistring ocitype oct octet_length of off offline offset oid oidindex old on online only opaque open operations operator optimal optimize option optionally or oracle oracle_date oradata ord ordaudio orddicom orddoc order ordimage ordinality ordvideo organization orlany orlvary out outer outfile outline output over overflow overriding package pad parallel parallel_enable parameters parent parse partial partition partitions pascal passing password password_grace_time password_lock_time password_reuse_max password_reuse_time password_verify_function patch path patindex pctincrease pctthreshold pctused pctversion percent percent_rank percentile_cont percentile_disc performance period period_add period_diff permanent physical pi pipe pipelined pivot pluggable plugin policy position post_transaction pow power pragma prebuilt precedes preceding precision prediction prediction_cost prediction_details prediction_probability prediction_set prepare present preserve prior priority private private_sga privileges procedural procedure procedure_analyze processlist profiles project prompt protection public publishingservername purge quarter query quick quiesce quota quotename radians raise rand range rank raw read reads readsize rebuild record records recover recovery recursive recycle redo reduced ref reference referenced references referencing refresh regexp_like register regr_avgx regr_avgy regr_count regr_intercept regr_r2 regr_slope regr_sxx regr_sxy reject rekey relational relative relaylog release release_lock relies_on relocate rely rem remainder rename repair repeat replace replicate replication required reset resetlogs resize resource respect restore restricted result result_cache resumable resume retention return returning returns reuse reverse revoke right rlike role roles rollback rolling rollup round row row_count rowdependencies rowid rownum rows rtrim rules safe salt sample save savepoint sb1 sb2 sb4 scan schema schemacheck scn scope scroll sdo_georaster sdo_topo_geometry search sec_to_time second section securefile security seed segment select self sequence sequential serializable server servererror session session_user sessions_per_user set sets settings sha sha1 sha2 share shared shared_pool short show shrink shutdown si_averagecolor si_colorhistogram si_featurelist si_positionalcolor si_stillimage si_texture siblings sid sign sin size size_t sizes skip slave sleep smalldatetimefromparts smallfile snapshot some soname sort soundex source space sparse spfile split sql sql_big_result sql_buffer_result sql_cache sql_calc_found_rows sql_small_result sql_variant_property sqlcode sqldata sqlerror sqlname sqlstate sqrt square standalone standby start starting startup statement static statistics stats_binomial_test stats_crosstab stats_ks_test stats_mode stats_mw_test stats_one_way_anova stats_t_test_ stats_t_test_indep stats_t_test_one stats_t_test_paired stats_wsr_test status std stddev stddev_pop stddev_samp stdev stop storage store stored str str_to_date straight_join strcmp strict string struct stuff style subdate subpartition subpartitions substitutable substr substring subtime subtring_index subtype success sum suspend switch switchoffset switchover sync synchronous synonym sys sys_xmlagg sysasm sysaux sysdate sysdatetimeoffset sysdba sysoper system system_user sysutcdatetime table tables tablespace tan tdo template temporary terminated tertiary_weights test than then thread through tier ties time time_format time_zone timediff timefromparts timeout timestamp timestampadd timestampdiff timezone_abbr timezone_minute timezone_region to to_base64 to_date to_days to_seconds todatetimeoffset trace tracking transaction transactional translate translation treat trigger trigger_nestlevel triggers trim truncate try_cast try_convert try_parse type ub1 ub2 ub4 ucase unarchived unbounded uncompress under undo unhex unicode uniform uninstall union unique unix_timestamp unknown unlimited unlock unpivot unrecoverable unsafe unsigned until untrusted unusable unused update updated upgrade upped upper upsert url urowid usable usage use use_stored_outlines user user_data user_resources users using utc_date utc_timestamp uuid uuid_short validate validate_password_strength validation valist value values var var_samp varcharc vari varia variab variabl variable variables variance varp varraw varrawc varray verify version versions view virtual visible void wait wallet warning warnings week weekday weekofyear wellformed when whene whenev wheneve whenever where while whitespace with within without work wrapped xdb xml xmlagg xmlattributes xmlcast xmlcolattval xmlelement xmlexists xmlforest xmlindex xmlnamespaces xmlpi xmlquery xmlroot xmlschema xmlserialize xmltable xmltype xor year year_to_month years yearweek\",literal:\"true false null\",built_in:\"array bigint binary bit blob boolean char character date dec decimal float int int8 integer interval number numeric real record serial serial8 smallint text varchar varying void\"},c:[{cN:\"string\",b:\"'\",e:\"'\",c:[e.BE,{b:\"''\"}]},{cN:\"string\",b:'\"',e:'\"',c:[e.BE,{b:'\"\"'}]},{cN:\"string\",b:\"`\",e:\"`\",c:[e.BE]},e.CNM,e.CBCM,t]},e.CBCM,t]}});hljs.registerLanguage(\"php\",function(e){var c={b:\"\\\\$+[a-zA-Z_\u007F-ÿ][a-zA-Z0-9_\u007F-ÿ]*\"},a={cN:\"meta\",b:/<\\?(php)?|\\?>/},i={cN:\"string\",c:[e.BE,a],v:[{b:'b\"',e:'\"'},{b:\"b'\",e:\"'\"},e.inherit(e.ASM,{i:null}),e.inherit(e.QSM,{i:null})]},t={v:[e.BNM,e.CNM]};return{aliases:[\"php3\",\"php4\",\"php5\",\"php6\"],cI:!0,k:\"and include_once list abstract global private echo interface as static endswitch array null if endwhile or const for endforeach self var while isset public protected exit foreach throw elseif include __FILE__ empty require_once do xor return parent clone use __CLASS__ __LINE__ else break print eval new catch __METHOD__ case exception default die require __FUNCTION__ enddeclare final try switch continue endfor endif declare unset true false trait goto instanceof insteadof __DIR__ __NAMESPACE__ yield finally\",c:[e.HCM,e.C(\"//\",\"$\",{c:[a]}),e.C(\"/\\\\*\",\"\\\\*/\",{c:[{cN:\"doctag\",b:\"@[A-Za-z]+\"}]}),e.C(\"__halt_compiler.+?;\",!1,{eW:!0,k:\"__halt_compiler\",l:e.UIR}),{cN:\"string\",b:/<<<['\"]?\\w+['\"]?$/,e:/^\\w+;?$/,c:[e.BE,{cN:\"subst\",v:[{b:/\\$\\w+/},{b:/\\{\\$/,e:/\\}/}]}]},a,c,{b:/(::|->)+[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*/},{cN:\"function\",bK:\"function\",e:/[;{]/,eE:!0,i:\"\\\\$|\\\\[|%\",c:[e.UTM,{cN:\"params\",b:\"\\\\(\",e:\"\\\\)\",c:[\"self\",c,e.CBCM,i,t]}]},{cN:\"class\",bK:\"class interface\",e:\"{\",eE:!0,i:/[:\\(\\$\"]/,c:[{bK:\"extends implements\"},e.UTM]},{bK:\"namespace\",e:\";\",i:/[\\.']/,c:[e.UTM]},{bK:\"use\",e:\";\",c:[e.UTM]},{b:\"=>\"},i,t]}});hljs.registerLanguage(\"cs\",function(e){var t=\"abstract as base bool break byte case catch char checked const continue decimal dynamic default delegate do double else enum event explicit extern false finally fixed float for foreach goto if implicit in int interface internal is lock long null when object operator out override params private protected public readonly ref sbyte sealed short sizeof stackalloc static string struct switch this true try typeof uint ulong unchecked unsafe ushort using virtual volatile void while async protected public private internal ascending descending from get group into join let orderby partial select set value var where yield\",r=e.IR+\"(<\"+e.IR+\">)?\";return{aliases:[\"csharp\"],k:t,i:/::/,c:[e.C(\"///\",\"$\",{rB:!0,c:[{cN:\"doctag\",v:[{b:\"///\",r:0},{b:\"<!--|-->\"},{b:\"</?\",e:\">\"}]}]}),e.CLCM,e.CBCM,{cN:\"meta\",b:\"#\",e:\"$\",k:{\"meta-keyword\":\"if else elif endif define undef warning error line region endregion pragma checksum\"}},{cN:\"string\",b:'@\"',e:'\"',c:[{b:'\"\"'}]},e.ASM,e.QSM,e.CNM,{bK:\"class interface\",e:/[{;=]/,i:/[^\\s:]/,c:[e.TM,e.CLCM,e.CBCM]},{bK:\"namespace\",e:/[{;=]/,i:/[^\\s:]/,c:[e.inherit(e.TM,{b:\"[a-zA-Z](\\\\.?\\\\w)*\"}),e.CLCM,e.CBCM]},{bK:\"new return throw await\",r:0},{cN:\"function\",b:\"(\"+r+\"\\\\s+)+\"+e.IR+\"\\\\s*\\\\(\",rB:!0,e:/[{;=]/,eE:!0,k:t,c:[{b:e.IR+\"\\\\s*\\\\(\",rB:!0,c:[e.TM],r:0},{cN:\"params\",b:/\\(/,e:/\\)/,eB:!0,eE:!0,k:t,r:0,c:[e.ASM,e.QSM,e.CNM,e.CBCM]},e.CLCM,e.CBCM]}]}});hljs.registerLanguage(\"ruby\",function(e){var b=\"[a-zA-Z_]\\\\w*[!?=]?|[-+~]\\\\@|<<|>>|=~|===?|<=>|[<>]=?|\\\\*\\\\*|[-/+%^&*~`|]|\\\\[\\\\]=?\",c=\"and false then defined module in return redo if BEGIN retry end for true self when next until do begin unless END rescue nil else break undef not super class case require yield alias while ensure elsif or include attr_reader attr_writer attr_accessor\",r={cN:\"doctag\",b:\"@[A-Za-z]+\"},a={b:\"#<\",e:\">\"},s=[e.C(\"#\",\"$\",{c:[r]}),e.C(\"^\\\\=begin\",\"^\\\\=end\",{c:[r],r:10}),e.C(\"^__END__\",\"\\\\n$\")],n={cN:\"subst\",b:\"#\\\\{\",e:\"}\",k:c},t={cN:\"string\",c:[e.BE,n],v:[{b:/'/,e:/'/},{b:/\"/,e:/\"/},{b:/`/,e:/`/},{b:\"%[qQwWx]?\\\\(\",e:\"\\\\)\"},{b:\"%[qQwWx]?\\\\[\",e:\"\\\\]\"},{b:\"%[qQwWx]?{\",e:\"}\"},{b:\"%[qQwWx]?<\",e:\">\"},{b:\"%[qQwWx]?/\",e:\"/\"},{b:\"%[qQwWx]?%\",e:\"%\"},{b:\"%[qQwWx]?-\",e:\"-\"},{b:\"%[qQwWx]?\\\\|\",e:\"\\\\|\"},{b:/\\B\\?(\\\\\\d{1,3}|\\\\x[A-Fa-f0-9]{1,2}|\\\\u[A-Fa-f0-9]{4}|\\\\?\\S)\\b/}]},i={cN:\"params\",b:\"\\\\(\",e:\"\\\\)\",endsParent:!0,k:c},d=[t,a,{cN:\"class\",bK:\"class module\",e:\"$|;\",i:/=/,c:[e.inherit(e.TM,{b:\"[A-Za-z_]\\\\w*(::\\\\w+)*(\\\\?|\\\\!)?\"}),{b:\"<\\\\s*\",c:[{b:\"(\"+e.IR+\"::)?\"+e.IR}]}].concat(s)},{cN:\"function\",bK:\"def\",e:\"$|;\",c:[e.inherit(e.TM,{b:b}),i].concat(s)},{cN:\"symbol\",b:e.UIR+\"(\\\\!|\\\\?)?:\",r:0},{cN:\"symbol\",b:\":\",c:[t,{b:b}],r:0},{cN:\"number\",b:\"(\\\\b0[0-7_]+)|(\\\\b0x[0-9a-fA-F_]+)|(\\\\b[1-9][0-9_]*(\\\\.[0-9_]+)?)|[0_]\\\\b\",r:0},{b:\"(\\\\$\\\\W)|((\\\\$|\\\\@\\\\@?)(\\\\w+))\"},{b:\"(\"+e.RSR+\")\\\\s*\",c:[a,{cN:\"regexp\",c:[e.BE,n],i:/\\n/,v:[{b:\"/\",e:\"/[a-z]*\"},{b:\"%r{\",e:\"}[a-z]*\"},{b:\"%r\\\\(\",e:\"\\\\)[a-z]*\"},{b:\"%r!\",e:\"![a-z]*\"},{b:\"%r\\\\[\",e:\"\\\\][a-z]*\"}]}].concat(s),r:0}].concat(s);n.c=d,i.c=d;var o=\"[>?]>\",l=\"[\\\\w#]+\\\\(\\\\w+\\\\):\\\\d+:\\\\d+>\",u=\"(\\\\w+-)?\\\\d+\\\\.\\\\d+\\\\.\\\\d(p\\\\d+)?[^>]+>\",w=[{b:/^\\s*=>/,starts:{e:\"$\",c:d}},{cN:\"meta\",b:\"^(\"+o+\"|\"+l+\"|\"+u+\")\",starts:{e:\"$\",c:d}}];return{aliases:[\"rb\",\"gemspec\",\"podspec\",\"thor\",\"irb\"],k:c,i:/\\/\\*/,c:s.concat(w).concat(d)}});hljs.registerLanguage(\"javascript\",function(e){return{aliases:[\"js\",\"jsx\"],k:{keyword:\"in of if for while finally var new function do return void else break catch instanceof with throw case default try this switch continue typeof delete let yield const export super debugger as async await static import from as\",literal:\"true false null undefined NaN Infinity\",built_in:\"eval isFinite isNaN parseFloat parseInt decodeURI decodeURIComponent encodeURI encodeURIComponent escape unescape Object Function Boolean Error EvalError InternalError RangeError ReferenceError StopIteration SyntaxError TypeError URIError Number Math Date String RegExp Array Float32Array Float64Array Int16Array Int32Array Int8Array Uint16Array Uint32Array Uint8Array Uint8ClampedArray ArrayBuffer DataView JSON Intl arguments require module console window document Symbol Set Map WeakSet WeakMap Proxy Reflect Promise\"},c:[{cN:\"meta\",r:10,b:/^\\s*['\"]use (strict|asm)['\"]/},{cN:\"meta\",b:/^#!/,e:/$/},e.ASM,e.QSM,{cN:\"string\",b:\"`\",e:\"`\",c:[e.BE,{cN:\"subst\",b:\"\\\\$\\\\{\",e:\"\\\\}\"}]},e.CLCM,e.CBCM,{cN:\"number\",v:[{b:\"\\\\b(0[bB][01]+)\"},{b:\"\\\\b(0[oO][0-7]+)\"},{b:e.CNR}],r:0},{b:\"(\"+e.RSR+\"|\\\\b(case|return|throw)\\\\b)\\\\s*\",k:\"return throw case\",c:[e.CLCM,e.CBCM,e.RM,{b:/</,e:/(\\/\\w+|\\w+\\/)>/,sL:\"xml\",c:[{b:/<\\w+\\/>/,skip:!0},{b:/<\\w+/,e:/(\\/\\w+|\\w+\\/)>/,skip:!0,c:[\"self\"]}]}],r:0},{cN:\"function\",bK:\"function\",e:/\\{/,eE:!0,c:[e.inherit(e.TM,{b:/[A-Za-z$_][0-9A-Za-z$_]*/}),{cN:\"params\",b:/\\(/,e:/\\)/,eB:!0,eE:!0,c:[e.CLCM,e.CBCM]}],i:/\\[|%/},{b:/\\$[(.]/},e.METHOD_GUARD,{cN:\"class\",bK:\"class\",e:/[{;=]/,eE:!0,i:/[:\"\\[\\]]/,c:[{bK:\"extends\"},e.UTM]},{bK:\"constructor\",e:/\\{/,eE:!0}],i:/#(?!!)/}});hljs.registerLanguage(\"java\",function(e){var a=e.UIR+\"(<\"+e.UIR+\"(\\\\s*,\\\\s*\"+e.UIR+\")*>)?\",t=\"false synchronized int abstract float private char boolean static null if const for true while long strictfp finally protected import native final void enum else break transient catch instanceof byte super volatile case assert short package default double public try this switch continue throws protected public private\",r=\"\\\\b(0[bB]([01]+[01_]+[01]+|[01]+)|0[xX]([a-fA-F0-9]+[a-fA-F0-9_]+[a-fA-F0-9]+|[a-fA-F0-9]+)|(([\\\\d]+[\\\\d_]+[\\\\d]+|[\\\\d]+)(\\\\.([\\\\d]+[\\\\d_]+[\\\\d]+|[\\\\d]+))?|\\\\.([\\\\d]+[\\\\d_]+[\\\\d]+|[\\\\d]+))([eE][-+]?\\\\d+)?)[lLfF]?\",c={cN:\"number\",b:r,r:0};return{aliases:[\"jsp\"],k:t,i:/<\\/|#/,c:[e.C(\"/\\\\*\\\\*\",\"\\\\*/\",{r:0,c:[{b:/\\w+@/,r:0},{cN:\"doctag\",b:\"@[A-Za-z]+\"}]}),e.CLCM,e.CBCM,e.ASM,e.QSM,{cN:\"class\",bK:\"class interface\",e:/[{;=]/,eE:!0,k:\"class interface\",i:/[:\"\\[\\]]/,c:[{bK:\"extends implements\"},e.UTM]},{bK:\"new throw return else\",r:0},{cN:\"function\",b:\"(\"+a+\"\\\\s+)+\"+e.UIR+\"\\\\s*\\\\(\",rB:!0,e:/[{;=]/,eE:!0,k:t,c:[{b:e.UIR+\"\\\\s*\\\\(\",rB:!0,r:0,c:[e.UTM]},{cN:\"params\",b:/\\(/,e:/\\)/,k:t,r:0,c:[e.ASM,e.QSM,e.CNM,e.CBCM]},e.CLCM,e.CBCM]},c,{cN:\"meta\",b:\"@[A-Za-z]+\"}]}});hljs.registerLanguage(\"python\",function(e){var r={cN:\"meta\",b:/^(>>>|\\.\\.\\.) /},b={cN:\"string\",c:[e.BE],v:[{b:/(u|b)?r?'''/,e:/'''/,c:[r],r:10},{b:/(u|b)?r?\"\"\"/,e:/\"\"\"/,c:[r],r:10},{b:/(u|r|ur)'/,e:/'/,r:10},{b:/(u|r|ur)\"/,e:/\"/,r:10},{b:/(b|br)'/,e:/'/},{b:/(b|br)\"/,e:/\"/},e.ASM,e.QSM]},a={cN:\"number\",r:0,v:[{b:e.BNR+\"[lLjJ]?\"},{b:\"\\\\b(0o[0-7]+)[lLjJ]?\"},{b:e.CNR+\"[lLjJ]?\"}]},l={cN:\"params\",b:/\\(/,e:/\\)/,c:[\"self\",r,a,b]};return{aliases:[\"py\",\"gyp\"],k:{keyword:\"and elif is global as in if from raise for except finally print import pass return exec else break not with class assert yield try while continue del or def lambda async await nonlocal|10 None True False\",built_in:\"Ellipsis NotImplemented\"},i:/(<\\/|->|\\?)/,c:[r,a,b,e.HCM,{v:[{cN:\"function\",bK:\"def\",r:10},{cN:\"class\",bK:\"class\"}],e:/:/,i:/[${=;\\n,]/,c:[e.UTM,l,{b:/->/,eW:!0,k:\"None\"}]},{cN:\"meta\",b:/^[\\t ]*@/,e:/$/},{b:/\\b(print|exec)\\(/}]}});hljs.registerLanguage(\"cpp\",function(t){var e={cN:\"keyword\",b:\"\\\\b[a-z\\\\d_]*_t\\\\b\"},r={cN:\"string\",v:[t.inherit(t.QSM,{b:'((u8?|U)|L)?\"'}),{b:'(u8?|U)?R\"',e:'\"',c:[t.BE]},{b:\"'\\\\\\\\?.\",e:\"'\",i:\".\"}]},i={cN:\"number\",v:[{b:\"\\\\b(\\\\d+(\\\\.\\\\d*)?|\\\\.\\\\d+)(u|U|l|L|ul|UL|f|F)\"},{b:t.CNR}],r:0},s={cN:\"meta\",b:\"#\",e:\"$\",k:{\"meta-keyword\":\"if else elif endif define undef warning error line pragma ifdef ifndef\"},c:[{b:/\\\\\\n/,r:0},{bK:\"include\",e:\"$\",k:{\"meta-keyword\":\"include\"},c:[t.inherit(r,{cN:\"meta-string\"}),{cN:\"meta-string\",b:\"<\",e:\">\",i:\"\\\\n\"}]},r,t.CLCM,t.CBCM]},a=t.IR+\"\\\\s*\\\\(\",c={keyword:\"int float while private char catch export virtual operator sizeof dynamic_cast|10 typedef const_cast|10 const struct for static_cast|10 union namespace unsigned long volatile static protected bool template mutable if public friend do goto auto void enum else break extern using class asm case typeid short reinterpret_cast|10 default double register explicit signed typename try this switch continue inline delete alignof constexpr decltype noexcept static_assert thread_local restrict _Bool complex _Complex _Imaginary atomic_bool atomic_char atomic_schar atomic_uchar atomic_short atomic_ushort atomic_int atomic_uint atomic_long atomic_ulong atomic_llong atomic_ullong\",built_in:\"std string cin cout cerr clog stdin stdout stderr stringstream istringstream ostringstream auto_ptr deque list queue stack vector map set bitset multiset multimap unordered_set unordered_map unordered_multiset unordered_multimap array shared_ptr abort abs acos asin atan2 atan calloc ceil cosh cos exit exp fabs floor fmod fprintf fputs free frexp fscanf isalnum isalpha iscntrl isdigit isgraph islower isprint ispunct isspace isupper isxdigit tolower toupper labs ldexp log10 log malloc realloc memchr memcmp memcpy memset modf pow printf putchar puts scanf sinh sin snprintf sprintf sqrt sscanf strcat strchr strcmp strcpy strcspn strlen strncat strncmp strncpy strpbrk strrchr strspn strstr tanh tan vfprintf vprintf vsprintf endl initializer_list unique_ptr\",literal:\"true false nullptr NULL\"};return{aliases:[\"c\",\"cc\",\"h\",\"c++\",\"h++\",\"hpp\"],k:c,i:\"</\",c:[e,t.CLCM,t.CBCM,i,r,s,{b:\"\\\\b(deque|list|queue|stack|vector|map|set|bitset|multiset|multimap|unordered_map|unordered_set|unordered_multiset|unordered_multimap|array)\\\\s*<\",e:\">\",k:c,c:[\"self\",e]},{b:t.IR+\"::\",k:c},{bK:\"new throw return else\",r:0},{cN:\"function\",b:\"(\"+t.IR+\"[\\\\*&\\\\s]+)+\"+a,rB:!0,e:/[{;=]/,eE:!0,k:c,i:/[^\\w\\s\\*&]/,c:[{b:a,rB:!0,c:[t.TM],r:0},{cN:\"params\",b:/\\(/,e:/\\)/,k:c,r:0,c:[t.CLCM,t.CBCM,r,i]},t.CLCM,t.CBCM,s]}]}});";



    final static String CSS_DEFAULT = "/*\n" +
            "\n" +
            "Original highlight.js style (c) Ivan Sagalaev <maniac@softwaremaniacs.org>\n" +
            "\n" +
            "*/\n" +
            "\n" +
            ".hljs {\n" +
            "  display: block;\n" +
            "  overflow-x: auto;\n" +
            "  padding: 0.5em;\n" +
            "  background: #F0F0F0;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* Base color: saturation 0; */\n" +
            "\n" +
            ".hljs,\n" +
            ".hljs-subst {\n" +
            "  color: #444;\n" +
            "}\n" +
            "\n" +
            ".hljs-comment {\n" +
            "  color: #888888;\n" +
            "}\n" +
            "\n" +
            ".hljs-keyword,\n" +
            ".hljs-attribute,\n" +
            ".hljs-selector-tag,\n" +
            ".hljs-meta-keyword,\n" +
            ".hljs-doctag,\n" +
            ".hljs-name {\n" +
            "  font-weight: bold;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* User color: hue: 0 */\n" +
            "\n" +
            ".hljs-type,\n" +
            ".hljs-string,\n" +
            ".hljs-number,\n" +
            ".hljs-selector-id,\n" +
            ".hljs-selector-class,\n" +
            ".hljs-quote,\n" +
            ".hljs-template-tag,\n" +
            ".hljs-deletion {\n" +
            "  color: #880000;\n" +
            "}\n" +
            "\n" +
            ".hljs-title,\n" +
            ".hljs-section {\n" +
            "  color: #880000;\n" +
            "  font-weight: bold;\n" +
            "}\n" +
            "\n" +
            ".hljs-regexp,\n" +
            ".hljs-symbol,\n" +
            ".hljs-variable,\n" +
            ".hljs-template-variable,\n" +
            ".hljs-link,\n" +
            ".hljs-selector-attr,\n" +
            ".hljs-selector-pseudo {\n" +
            "  color: #BC6060;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* Language color: hue: 90; */\n" +
            "\n" +
            ".hljs-literal {\n" +
            "  color: #78A960;\n" +
            "}\n" +
            "\n" +
            ".hljs-built_in,\n" +
            ".hljs-bullet,\n" +
            ".hljs-code,\n" +
            ".hljs-addition {\n" +
            "  color: #397300;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* Meta color: hue: 200 */\n" +
            "\n" +
            ".hljs-meta {\n" +
            "  color: #1f7199;\n" +
            "}\n" +
            "\n" +
            ".hljs-meta-string {\n" +
            "  color: #4d99bf;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* Misc effects */\n" +
            "\n" +
            ".hljs-emphasis {\n" +
            "  font-style: italic;\n" +
            "}\n" +
            "\n" +
            ".hljs-strong {\n" +
            "  font-weight: bold;\n" +
            "}\n";

        final static String CSS_AGATE = "/*!\n" +
                " * Agate by Taufik Nurrohman <https://github.com/tovic>\n" +
                " * ----------------------------------------------------\n" +
                " *\n" +
                " * #ade5fc\n" +
                " * #a2fca2\n" +
                " * #c6b4f0\n" +
                " * #d36363\n" +
                " * #fcc28c\n" +
                " * #fc9b9b\n" +
                " * #ffa\n" +
                " * #fff\n" +
                " * #333\n" +
                " * #62c8f3\n" +
                " * #888\n" +
                " *\n" +
                " */\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #333;\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                ".hljs-name,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-code,\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag {\n" +
                "  color: #62c8f3;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #ade5fc;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-bullet {\n" +
                "  color: #a2fca2;\n" +
                "}\n" +
                "\n" +
                ".hljs-type,\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-attribute,\n" +
                ".hljs-quote,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name {\n" +
                "  color: #ffa;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #d36363;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal {\n" +
                "  color: #fcc28c;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-deletion,\n" +
                ".hljs-code {\n" +
                "  color: #888;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  color: #c6b4f0;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #fc9b9b;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background-color: #fc9b9b;\n" +
                "  color: #333;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background-color: #a2fca2;\n" +
                "  color: #333;\n" +
                "}\n" +
                "\n" +
                ".hljs a {\n" +
                "  color: inherit;\n" +
                "}\n" +
                "\n" +
                ".hljs a:focus,\n" +
                ".hljs a:hover {\n" +
                "  color: inherit;\n" +
                "  text-decoration: underline;\n" +
                "}\n";


        final static String CSS_ANDROIDSTUDIO = "/*\n" +
                "Date: 24 Fev 2015\n" +
                "Author: Pedro Oliveira <kanytu@gmail . com>\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  color: #a9b7c6;\n" +
                "  background: #282b2e;\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-literal,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #6897BB;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-deletion {\n" +
                "  color: #cc7832;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-link {\n" +
                "  color: #629755;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #808080;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #bbb529;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-attribute,\n" +
                ".hljs-addition {\n" +
                "  color: #6A8759;\n" +
                "}\n" +
                "\n" +
                ".hljs-section,\n" +
                ".hljs-title,\n" +
                ".hljs-type {\n" +
                "  color: #ffc66d;\n" +
                "}\n" +
                "\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #e8bf6a;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_ARDUINO = "/*\n" +
                "\n" +
                "Arduino® Light Theme - Stefania Mellai <s.mellai@arduino.cc>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #FFFFFF;\n" +
                "}\n" +
                "\n" +
                ".hljs,\n" +
                ".hljs-subst {\n" +
                "  color: #434f54;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-attribute,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-doctag,\n" +
                ".hljs-name {\n" +
                "  color: #00979D;\n" +
                "}\n" +
                "\n" +
                ".hljs-built_in,\n" +
                ".hljs-literal,\n" +
                ".hljs-bullet,\n" +
                ".hljs-code,\n" +
                ".hljs-addition {\n" +
                "  color: #D35400;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-symbol,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-link,\n" +
                ".hljs-selector-attr,\n" +
                ".hljs-selector-pseudo {\n" +
                "  color: #00979D;\n" +
                "}\n" +
                "\n" +
                ".hljs-type,\n" +
                ".hljs-string,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class,\n" +
                ".hljs-quote,\n" +
                ".hljs-template-tag,\n" +
                ".hljs-deletion {\n" +
                "  color: #005C5F;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-section {\n" +
                "  color: #880000;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment {\n" +
                "  color: rgba(149,165,166,.8);\n" +
                "}\n" +
                "\n" +
                ".hljs-meta-keyword {\n" +
                "  color: #728E00;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #728E00;\n" +
                "  color: #434f54;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-function {\n" +
                "  color: #728E00;\n" +
                "}\n" +
                "\n" +
                ".hljs-number {\n" +
                "  color: #8A7B52;  \n" +
                "}\n";



        final static String CSS_ARTA = "/*\n" +
                "Date: 17.V.2011\n" +
                "Author: pumbur <pumbur@pumbur.net>\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #222;\n" +
                "}\n" +
                "\n" +
                ".hljs,\n" +
                ".hljs-subst {\n" +
                "  color: #aaa;\n" +
                "}\n" +
                "\n" +
                ".hljs-section {\n" +
                "  color: #fff;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote,\n" +
                ".hljs-meta {\n" +
                "  color: #444;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-regexp {\n" +
                "  color: #ffcc33;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-addition {\n" +
                "  color: #00cc66;\n" +
                "}\n" +
                "\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-literal,\n" +
                ".hljs-type,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-attribute,\n" +
                ".hljs-link {\n" +
                "  color: #32aaee;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #6644aa;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-variable,\n" +
                ".hljs-deletion,\n" +
                ".hljs-template-tag {\n" +
                "  color: #bb1166;\n" +
                "}\n" +
                "\n" +
                ".hljs-section,\n" +
                ".hljs-doctag,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";

        final static String CSS_ASCETIC = "/*\n" +
                "\n" +
                "Original style from softwaremaniacs.org (c) Ivan Sagalaev <Maniac@SoftwareManiacs.Org>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: white;\n" +
                "  color: black;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-section,\n" +
                ".hljs-addition,\n" +
                ".hljs-attribute,\n" +
                ".hljs-link {\n" +
                "  color: #888;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote,\n" +
                ".hljs-meta,\n" +
                ".hljs-deletion {\n" +
                "  color: #ccc;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-section,\n" +
                ".hljs-name,\n" +
                ".hljs-type,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";

        final static String CSS_ATELIER_DARK = "/* Base16 Atelier Cave Dark - Theme */\n" +
                "/* by Bram de Haan (http://atelierbram.github.io/syntax-highlighting/atelier-schemes/cave) */\n" +
                "/* Original Base16 color scheme by Chris Kempson (https://github.com/chriskempson/base16) */\n" +
                "\n" +
                "/* Atelier-Cave Comment */\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #7e7887;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Red */\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-attribute,\n" +
                ".hljs-regexp,\n" +
                ".hljs-link,\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #be4678;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Orange */\n" +
                ".hljs-number,\n" +
                ".hljs-meta,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-literal,\n" +
                ".hljs-type,\n" +
                ".hljs-params {\n" +
                "  color: #aa573c;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Green */\n" +
                ".hljs-string,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #2a9292;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Blue */\n" +
                ".hljs-title,\n" +
                ".hljs-section {\n" +
                "  color: #576ddb;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Purple */\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag {\n" +
                "  color: #955ae7;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion,\n" +
                ".hljs-addition {\n" +
                "  color: #19171c;\n" +
                "  display: inline-block;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background-color: #be4678;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background-color: #2a9292;\n" +
                "}\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  background: #19171c;\n" +
                "  color: #8b8792;\n" +
                "  padding: 0.5em;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_ATELIER_LIGHT = "/* Base16 Atelier Cave Light - Theme */\n" +
                "/* by Bram de Haan (http://atelierbram.github.io/syntax-highlighting/atelier-schemes/cave) */\n" +
                "/* Original Base16 color scheme by Chris Kempson (https://github.com/chriskempson/base16) */\n" +
                "\n" +
                "/* Atelier-Cave Comment */\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #655f6d;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Red */\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-attribute,\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-regexp,\n" +
                ".hljs-link,\n" +
                ".hljs-name,\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #be4678;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Orange */\n" +
                ".hljs-number,\n" +
                ".hljs-meta,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-literal,\n" +
                ".hljs-type,\n" +
                ".hljs-params {\n" +
                "  color: #aa573c;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Green */\n" +
                ".hljs-string,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #2a9292;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Blue */\n" +
                ".hljs-title,\n" +
                ".hljs-section {\n" +
                "  color: #576ddb;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Cave Purple */\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag {\n" +
                "  color: #955ae7;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion,\n" +
                ".hljs-addition {\n" +
                "  color: #19171c;\n" +
                "  display: inline-block;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background-color: #be4678;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background-color: #2a9292;\n" +
                "}\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  background: #efecf4;\n" +
                "  color: #585260;\n" +
                "  padding: 0.5em;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_ATELIER_FOREST_DARK = "/* Base16 Atelier Forest Dark - Theme */\n" +
                "/* by Bram de Haan (http://atelierbram.github.io/syntax-highlighting/atelier-schemes/forest) */\n" +
                "/* Original Base16 color scheme by Chris Kempson (https://github.com/chriskempson/base16) */\n" +
                "\n" +
                "/* Atelier-Forest Comment */\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #9c9491;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Forest Red */\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-attribute,\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-regexp,\n" +
                ".hljs-link,\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #f22c40;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Forest Orange */\n" +
                ".hljs-number,\n" +
                ".hljs-meta,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-literal,\n" +
                ".hljs-type,\n" +
                ".hljs-params {\n" +
                "  color: #df5320;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Forest Green */\n" +
                ".hljs-string,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #7b9726;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Forest Blue */\n" +
                ".hljs-title,\n" +
                ".hljs-section {\n" +
                "  color: #407ee7;\n" +
                "}\n" +
                "\n" +
                "/* Atelier-Forest Purple */\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag {\n" +
                "  color: #6666ea;\n" +
                "}\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  background: #1b1918;\n" +
                "  color: #a8a19f;\n" +
                "  padding: 0.5em;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_DARKSTYLE = "/*\n" +
                "\n" +
                "Dark style from softwaremaniacs.org (c) Ivan Sagalaev <Maniac@SoftwareManiacs.Org>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #444;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal,\n" +
                ".hljs-section,\n" +
                ".hljs-link {\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                ".hljs,\n" +
                ".hljs-subst {\n" +
                "  color: #ddd;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-title,\n" +
                ".hljs-name,\n" +
                ".hljs-type,\n" +
                ".hljs-attribute,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-built_in,\n" +
                ".hljs-addition,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-tag,\n" +
                ".hljs-template-variable {\n" +
                "  color: #d88;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote,\n" +
                ".hljs-deletion,\n" +
                ".hljs-meta {\n" +
                "  color: #777;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal,\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-doctag,\n" +
                ".hljs-type,\n" +
                ".hljs-name,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";




    final static String CSS_DARKULA = "/*\n" +
            "\n" +
            "Darkula color scheme from the JetBrains family of IDEs\n" +
            "\n" +
            "*/\n" +
            "\n" +
            "\n" +
            ".hljs {\n" +
            "  display: block;\n" +
            "  overflow-x: auto;\n" +
            "  padding: 0.5em;\n" +
            "  background: #2b2b2b;\n" +
            "}\n" +
            "\n" +
            ".hljs {\n" +
            "  color: #bababa;\n" +
            "}\n" +
            "\n" +
            ".hljs-strong,\n" +
            ".hljs-emphasis {\n" +
            "  color: #a8a8a2;\n" +
            "}\n" +
            "\n" +
            ".hljs-bullet,\n" +
            ".hljs-quote,\n" +
            ".hljs-link,\n" +
            ".hljs-number,\n" +
            ".hljs-regexp,\n" +
            ".hljs-literal {\n" +
            "  color: #6896ba;\n" +
            "}\n" +
            "\n" +
            ".hljs-code,\n" +
            ".hljs-selector-class {\n" +
            "  color: #a6e22e;\n" +
            "}\n" +
            "\n" +
            ".hljs-emphasis {\n" +
            "  font-style: italic;\n" +
            "}\n" +
            "\n" +
            ".hljs-keyword,\n" +
            ".hljs-selector-tag,\n" +
            ".hljs-section,\n" +
            ".hljs-attribute,\n" +
            ".hljs-name,\n" +
            ".hljs-variable {\n" +
            "  color: #cb7832;\n" +
            "}\n" +
            "\n" +
            ".hljs-params {\n" +
            "  color: #b9b9b9;\n" +
            "}\n" +
            "\n" +
            ".hljs-string,\n" +
            ".hljs-subst,\n" +
            ".hljs-type,\n" +
            ".hljs-built_in,\n" +
            ".hljs-builtin-name,\n" +
            ".hljs-symbol,\n" +
            ".hljs-selector-id,\n" +
            ".hljs-selector-attr,\n" +
            ".hljs-selector-pseudo,\n" +
            ".hljs-template-tag,\n" +
            ".hljs-template-variable,\n" +
            ".hljs-addition {\n" +
            "  color: #e0c46c;\n" +
            "}\n" +
            "\n" +
            ".hljs-comment,\n" +
            ".hljs-deletion,\n" +
            ".hljs-meta {\n" +
            "  color: #7f7f7f;\n" +
            "}\n";


        final static String CSS_DOCCO = "/*\n" +
                "Docco style used in http://jashkenas.github.com/docco/ converted by Simon Madine (@thingsinjars)\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  color: #000;\n" +
                "  background: #f8f8ff;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #408080;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal,\n" +
                ".hljs-subst {\n" +
                "  color: #954121;\n" +
                "}\n" +
                "\n" +
                ".hljs-number {\n" +
                "  color: #40a070;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-doctag {\n" +
                "  color: #219161;\n" +
                "}\n" +
                "\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class,\n" +
                ".hljs-section,\n" +
                ".hljs-type {\n" +
                "  color: #19469d;\n" +
                "}\n" +
                "\n" +
                ".hljs-params {\n" +
                "  color: #00f;\n" +
                "}\n" +
                "\n" +
                ".hljs-title {\n" +
                "  color: #458;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-attribute {\n" +
                "  color: #000080;\n" +
                "  font-weight: normal;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable {\n" +
                "  color: #008080;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  color: #b68;\n" +
                "}\n" +
                "\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #990073;\n" +
                "}\n" +
                "\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name {\n" +
                "  color: #0086b3;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #999;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background: #fdd;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background: #dfd;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_FAR = "/*\n" +
                "\n" +
                "FAR Style (c) MajestiC <majestic2k@gmail.com>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #000080;\n" +
                "}\n" +
                "\n" +
                ".hljs,\n" +
                ".hljs-subst {\n" +
                "  color: #0ff;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-attribute,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-template-tag,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-addition {\n" +
                "  color: #ff0;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-section,\n" +
                ".hljs-type,\n" +
                ".hljs-name,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class,\n" +
                ".hljs-variable {\n" +
                "  color: #fff;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote,\n" +
                ".hljs-doctag,\n" +
                ".hljs-deletion {\n" +
                "  color: #888;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-regexp,\n" +
                ".hljs-literal,\n" +
                ".hljs-link {\n" +
                "  color: #0f0;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #008080;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-name,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";

        final static String CSS_GITHUB = "/*\n" +
                "\n" +
                "github.com style (c) Vasily Polovnyov <vast@whiteants.net>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  color: #333;\n" +
                "  background: #f8f8f8;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #998;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-subst {\n" +
                "  color: #333;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-literal,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-tag .hljs-attr {\n" +
                "  color: #008080;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-doctag {\n" +
                "  color: #d14;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-selector-id {\n" +
                "  color: #900;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-subst {\n" +
                "  font-weight: normal;\n" +
                "}\n" +
                "\n" +
                ".hljs-type,\n" +
                ".hljs-class .hljs-title {\n" +
                "  color: #458;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-attribute {\n" +
                "  color: #000080;\n" +
                "  font-weight: normal;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  color: #009926;\n" +
                "}\n" +
                "\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet {\n" +
                "  color: #990073;\n" +
                "}\n" +
                "\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name {\n" +
                "  color: #0086b3;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #999;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background: #fdd;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background: #dfd;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_GIHUBGIST = "/**\n" +
                " * GitHub Gist Theme\n" +
                " * Author : Louis Barranqueiro - https://github.com/LouisBarranqueiro\n" +
                " */\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  background: white;\n" +
                "  padding: 0.5em;\n" +
                "  color: #333333;\n" +
                "  overflow-x: auto;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-meta {\n" +
                "  color: #969896;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable,\n" +
                ".hljs-strong,\n" +
                ".hljs-emphasis,\n" +
                ".hljs-quote {\n" +
                "  color: #df5000;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-type {\n" +
                "  color: #a71d5d;\n" +
                "}\n" +
                "\n" +
                ".hljs-literal,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-attribute {\n" +
                "  color: #0086b3;\n" +
                "}\n" +
                "\n" +
                ".hljs-section,\n" +
                ".hljs-name {\n" +
                "  color: #63a35c;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag {\n" +
                "  color: #333333;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-attr,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class,\n" +
                ".hljs-selector-attr,\n" +
                ".hljs-selector-pseudo {\n" +
                "  color: #795da3;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  color: #55a532;\n" +
                "  background-color: #eaffea;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  color: #bd2c00;\n" +
                "  background-color: #ffecec;\n" +
                "}\n" +
                "\n" +
                ".hljs-link {\n" +
                "  text-decoration: underline;\n" +
                "}\n";

        final static String CSS_GOOGLECODE = "/*\n" +
                "\n" +
                "Google Code style (c) Aahan Krish <geekpanth3r@gmail.com>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: white;\n" +
                "  color: black;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #800;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-section,\n" +
                ".hljs-title,\n" +
                ".hljs-name {\n" +
                "  color: #008;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable {\n" +
                "  color: #660;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-selector-attr,\n" +
                ".hljs-selector-pseudo,\n" +
                ".hljs-regexp {\n" +
                "  color: #080;\n" +
                "}\n" +
                "\n" +
                ".hljs-literal,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-meta,\n" +
                ".hljs-number,\n" +
                ".hljs-link {\n" +
                "  color: #066;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-doctag,\n" +
                ".hljs-type,\n" +
                ".hljs-attr,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-params {\n" +
                "  color: #606;\n" +
                "}\n" +
                "\n" +
                ".hljs-attribute,\n" +
                ".hljs-subst {\n" +
                "  color: #000;\n" +
                "}\n" +
                "\n" +
                ".hljs-formula {\n" +
                "  background-color: #eee;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #9B703F\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background-color: #baeeba;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background-color: #ffc8bd;\n" +
                "}\n" +
                "\n" +
                ".hljs-doctag,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";

        final static String CSS_IDEA = "/*\n" +
                "\n" +
                "Intellij Idea-like styling (c) Vasily Polovnyov <vast@whiteants.net>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  color: #000;\n" +
                "  background: #fff;\n" +
                "}\n" +
                "\n" +
                ".hljs-subst,\n" +
                ".hljs-title {\n" +
                "  font-weight: normal;\n" +
                "  color: #000;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #808080;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #808000;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag {\n" +
                "  background: #efefef;\n" +
                "}\n" +
                "\n" +
                ".hljs-section,\n" +
                ".hljs-name,\n" +
                ".hljs-literal,\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-type,\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  font-weight: bold;\n" +
                "  color: #000080;\n" +
                "}\n" +
                "\n" +
                ".hljs-attribute,\n" +
                ".hljs-number,\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  font-weight: bold;\n" +
                "  color: #0000ff;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  font-weight: normal;\n" +
                "}\n" +
                "\n" +
                ".hljs-string {\n" +
                "  color: #008000;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-formula {\n" +
                "  color: #000;\n" +
                "  background: #d0eded;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-doctag {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable {\n" +
                "  color: #660e7a;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background: #baeeba;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background: #ffc8bd;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_MAGULA = "/*\n" +
                "Description: Magula style for highligh.js\n" +
                "Author: Ruslan Keba <rukeba@gmail.com>\n" +
                "Website: http://rukeba.com/\n" +
                "Version: 1.0\n" +
                "Date: 2009-01-03\n" +
                "Music: Aphex Twin / Xtal\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background-color: #f4f4f4;\n" +
                "}\n" +
                "\n" +
                ".hljs,\n" +
                ".hljs-subst {\n" +
                "  color: black;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-title,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-attribute,\n" +
                ".hljs-addition,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-tag,\n" +
                ".hljs-template-variable {\n" +
                "  color: #050;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #777;\n" +
                "}\n" +
                "\n" +
                ".hljs-number,\n" +
                ".hljs-regexp,\n" +
                ".hljs-literal,\n" +
                ".hljs-type,\n" +
                ".hljs-link {\n" +
                "  color: #800;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion,\n" +
                ".hljs-meta {\n" +
                "  color: #00e;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-doctag,\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-built_in,\n" +
                ".hljs-tag,\n" +
                ".hljs-name {\n" +
                "  font-weight: bold;\n" +
                "  color: navy;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_OBSIDIAN = "/**\n" +
                " * Obsidian style\n" +
                " * ported by Alexander Marenin (http://github.com/ioncreature)\n" +
                " */\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #282b2e;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal,\n" +
                ".hljs-selector-id {\n" +
                "  color: #93c763;\n" +
                "}\n" +
                "\n" +
                ".hljs-number {\n" +
                "  color: #ffcd22;\n" +
                "}\n" +
                "\n" +
                ".hljs {\n" +
                "  color: #e0e2e4;\n" +
                "}\n" +
                "\n" +
                ".hljs-attribute {\n" +
                "  color: #668bb0;\n" +
                "}\n" +
                "\n" +
                ".hljs-code,\n" +
                ".hljs-class .hljs-title,\n" +
                ".hljs-section {\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  color: #d39745;\n" +
                "}\n" +
                "\n" +
                ".hljs-meta {\n" +
                "  color: #557182;\n" +
                "}\n" +
                "\n" +
                ".hljs-tag,\n" +
                ".hljs-name,\n" +
                ".hljs-bullet,\n" +
                ".hljs-subst,\n" +
                ".hljs-emphasis,\n" +
                ".hljs-type,\n" +
                ".hljs-built_in,\n" +
                ".hljs-selector-attr,\n" +
                ".hljs-selector-pseudo,\n" +
                ".hljs-addition,\n" +
                ".hljs-variable,\n" +
                ".hljs-template-tag,\n" +
                ".hljs-template-variable {\n" +
                "  color: #8cbbad;\n" +
                "}\n" +
                "\n" +
                ".hljs-string,\n" +
                ".hljs-symbol {\n" +
                "  color: #ec7600;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote,\n" +
                ".hljs-deletion {\n" +
                "  color: #818e96;\n" +
                "}\n" +
                "\n" +
                ".hljs-selector-class {\n" +
                "  color: #A082BD\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal,\n" +
                ".hljs-doctag,\n" +
                ".hljs-title,\n" +
                ".hljs-section,\n" +
                ".hljs-type,\n" +
                ".hljs-name,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n";

        final static String CSS_XCODE = "/*\n" +
                "\n" +
                "XCode style (c) Angel Garcia <angelgarcia.mail@gmail.com>\n" +
                "\n" +
                "*/\n" +
                "\n" +
                ".hljs {\n" +
                "  display: block;\n" +
                "  overflow-x: auto;\n" +
                "  padding: 0.5em;\n" +
                "  background: #fff;\n" +
                "  color: black;\n" +
                "}\n" +
                "\n" +
                ".hljs-comment,\n" +
                ".hljs-quote {\n" +
                "  color: #006a00;\n" +
                "}\n" +
                "\n" +
                ".hljs-keyword,\n" +
                ".hljs-selector-tag,\n" +
                ".hljs-literal {\n" +
                "  color: #aa0d91;\n" +
                "}\n" +
                "\n" +
                ".hljs-name {\n" +
                "  color: #008;\n" +
                "}\n" +
                "\n" +
                ".hljs-variable,\n" +
                ".hljs-template-variable {\n" +
                "  color: #660;\n" +
                "}\n" +
                "\n" +
                ".hljs-string {\n" +
                "  color: #c41a16;\n" +
                "}\n" +
                "\n" +
                ".hljs-regexp,\n" +
                ".hljs-link {\n" +
                "  color: #080;\n" +
                "}\n" +
                "\n" +
                ".hljs-title,\n" +
                ".hljs-tag,\n" +
                ".hljs-symbol,\n" +
                ".hljs-bullet,\n" +
                ".hljs-number,\n" +
                ".hljs-meta {\n" +
                "  color: #1c00cf;\n" +
                "}\n" +
                "\n" +
                ".hljs-section,\n" +
                ".hljs-class .hljs-title,\n" +
                ".hljs-type,\n" +
                ".hljs-attr,\n" +
                ".hljs-built_in,\n" +
                ".hljs-builtin-name,\n" +
                ".hljs-params {\n" +
                "  color: #5c2699;\n" +
                "}\n" +
                "\n" +
                ".hljs-attribute,\n" +
                ".hljs-subst {\n" +
                "  color: #000;\n" +
                "}\n" +
                "\n" +
                ".hljs-formula {\n" +
                "  background-color: #eee;\n" +
                "  font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".hljs-addition {\n" +
                "  background-color: #baeeba;\n" +
                "}\n" +
                "\n" +
                ".hljs-deletion {\n" +
                "  background-color: #ffc8bd;\n" +
                "}\n" +
                "\n" +
                ".hljs-selector-id,\n" +
                ".hljs-selector-class {\n" +
                "  color: #9b703f;\n" +
                "}\n" +
                "\n" +
                ".hljs-doctag,\n" +
                ".hljs-strong {\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".hljs-emphasis {\n" +
                "  font-style: italic;\n" +
                "}\n";


}
