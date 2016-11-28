from threading import Semaphore
from functools import partial
import json

from openvas_lib import VulnscanManager, VulnscanException

manager = VulnscanManager("localhost", "admin", "openvas")

def print_status(i):
    print(str(i))

def get_result(scan_id):
    print("get_result " + scan_id)
    openvas_results = manager.get_results(scan_id)
    results = []
    for opr in openvas_results:
        results.append({
            "id": opr.id,
            "subnet": opr.subnet,
            "host": opr.host,
            "port": {
                "port_name": opr.port.port_name,
                "number": opr.port.number,
                "proto": opr.port.proto
            },
            "nvt": {
                "oid" : opr.nvt.oid,
                "name" : opr.nvt.name,
                "cvss_base" : opr.nvt.cvss_base,
                "cvss_base_vector" : opr.nvt.cvss_base_vector,
                "risk_factor" : opr.nvt.risk_factor,
                "category" : opr.nvt.category,
                "summary" : opr.nvt.summary,
                "description" : opr.nvt.description,
                "family" : opr.nvt.family,
                "cves" : opr.nvt.cve,
                "bids" : opr.nvt.bid,
                "bugtraqs" : opr.nvt.bugtraq,
                "xrefs" : opr.nvt.xrefs,
                "fingerprints" : opr.nvt.fingerprints,
                "tags" : opr.nvt.tags
            },
            "threat": opr.threat,
            "description": opr.raw_description,
            "notes": opr.notes,
            "overrides": opr.overrides,
            "impact": opr.impact,
            "summary": opr.summary,
            "vulnerability_insight": opr.vulnerability_insight,
            "affected_software": opr.affected_software,
            "solution": opr.solution
        })
    return results


def get_results():
    tasks = ["a0a82ff2-7453-4f83-83a1-37b3618c8c3a","7ddb2157-13c4-4888-a05a-10882ee7a81f","a0e2f081-1778-4be6-925f-d4e489ea1f43","f3bc49f2-1839-4d90-a98b-290ddbcbf528","ae63fc0f-7be2-4bd3-8e98-ad6769db4348","84c5ab21-4b3f-4c52-95bd-2c0a9d5c1133","ad60fb37-6404-4925-8269-e9c22a5c32a7","be6f6369-9980-4f09-81fe-1676b26936e8","5aac9e70-e5aa-474a-9234-73647ec5a569","65a9fe81-07a4-456e-894a-2a718db9508c","65ebdc10-9f1b-49dc-9c01-bf4042110094","db5e4bc4-ec57-4ab6-88cb-3e42a1d62c50","0a2af82a-157a-4eea-87f8-c75c34771403","8005a54c-5041-4c87-9778-b5be3a6037b6","0286b991-3890-4a19-890f-7361d649cbaa","cc7eda3c-34cd-4c2b-a39c-a98bf9b59349","550179e1-0548-4193-8a40-5529599f43f3","733edc1f-ebcf-4079-8e06-4fec51a607c3","10b908c3-0819-434b-b60c-4acbc83cf758","be0f9fcd-8175-4477-a64a-170234a960a0","f1ba9a2f-cb1d-4a0e-9bf7-2d7e74e59af0","47ca1622-1d1a-49f7-abc5-a302fd667671","2b677eb9-345d-44b3-8415-24bccf0553a1","dcd86e0c-2a87-46fe-b4ef-2c53c9594177","a1b9d258-bb87-47ea-8b61-1018509766bf","f10f6703-35e3-4d34-af43-7723966b0f5b","03182250-d328-4f91-9842-e0ce3c3196d7","0ba1d4e6-b4bb-420b-92eb-bf616c71dd7e","ff31b0eb-2556-4fdd-87a2-97dae297d09b","4044bf9d-7dac-4cbd-b4bd-2bbebad4383d","f8070ba5-3612-4139-978f-e3ba6261109b","5c88fc6f-8672-40ba-8787-100ed603a6df","d1aa1ca1-8041-4686-8b34-5264699f208d","9934c758-038e-43ee-a6f3-6b23b784c44e","20f8ccd7-f9fb-4d71-a679-b570471cabb9","f44934c0-7bae-4910-a813-d4312bc3aab2","65bb8ea4-174d-48c7-974c-78cc9c0ba823","da7c53fd-99a9-4e6c-951b-5c6205555726","29207c52-8ea8-4cfd-93e5-9a2c70168c35","4c423369-323f-4fa2-9b7f-464ef0d83547","970ad96d-05bf-479b-b4c2-de976a7b78c3","61ff9507-b952-4fce-ac2d-4d771bf810e8","6a629afc-8176-4e1d-ab9c-be584f42c3b2","c27ee3a9-579a-425e-b67f-c62f38f5ccc2","4dc18bf2-4f58-4917-9549-911c4b70db2d","639c8477-ddb1-4b81-adbc-99ab82bded7b","73de17fb-0645-46f7-94c8-569bee9dffba","77360d6f-37de-4008-bcba-bb3f8d73601f","02b07349-ce28-4fc0-ac68-0b1f8fff8ad1","2f632a96-35fa-4d54-9174-c1fda6fc554e","4ebf1751-e422-4c3b-93f2-975ee738c314","435b860e-d4cf-42f0-beee-2b9f3ded61f9","3802c716-91b0-4ca3-8620-c382ea81b1e2","dd4e46f3-8f25-4d01-a4af-854542c4057c","ec8a79c4-fbc1-444f-a71e-ca4c739bc01d","284d6bc1-4a99-45dd-a466-4a3c3292a29b","5328b524-e2ee-4842-9777-62d6c40238a5","4b4040ac-0015-40e4-ac23-86eef295abba","a93d62a4-1222-4e2b-98c9-9960036a4748","544fcc39-5127-4dce-9781-765e1964da0e","821d426f-f3dc-4c84-ad3f-623c9e159b17","92c553af-b89e-4a0f-b1f6-ffe822d6dd88","f6ec6b64-1457-460a-ab37-2eaed36297a3","12ea5aa2-aae3-48a7-9be0-c01b2d49248a","a1ad635f-4aef-4a8a-baf2-b1b99550570f","afe23ebb-c612-41e8-baf7-d3ec6dd7f6ca","986c7b7d-6da9-4cc8-bf19-9d5bfb0d70ce","ef538df2-bd21-4a03-ac9e-11724f0ed707","89fdd0b9-05ed-468a-bfaf-1599f0e64558","f53e787c-7bab-4f10-a531-e8a5ecb67f4a","0db90185-fea8-42a0-af75-4a085a7e38a0","2f6eb346-d6b4-4c20-8afe-8835598cff40","6927c5ef-7601-4d9a-b1d5-3c3ea08c7b3d","8f6366ee-25db-4cdc-9985-c87b01cb0c34","6f499af3-897f-46ad-b033-a9ac349f0d94","94f22cca-6638-4de6-9e81-3f278182349b","1114166f-deb5-4cbe-bea1-fd33565873e4","6bb72ca8-cdf9-4d9b-9d1c-0f1eac897046","8cad127b-ba74-4d05-8c76-67a87380f447","25517a47-9720-421d-91f5-b1d909782f8a","fd6112b6-2976-46ce-ba48-d170ceb39103","4f70c08d-3f19-462a-a5ed-fc0c667302f0","1d12e04b-fc52-4b44-b189-241852134a3b","127ed4f3-8d27-45a4-963c-6c05b77921d2","9a26038a-0002-406b-8684-30ee86effc38","bfe44202-f02d-4b32-8a58-05c8706e0742","95cab6ff-9f53-4dd2-b67e-65f96d2752e2","b18f16e8-e417-4d14-a953-78ee1cd46a81","4d31c55a-68b7-4475-97f5-5ad6b8285e88","bd8f9353-33b2-48dc-8641-b7f3f824901b","25fdc873-1153-44a8-9195-2d2571da9377","8d806e0c-714d-43e5-ad74-2a7f51237dfe","7e9a4643-9f2d-4eda-b1cb-add06fa43bc9","c7d494c4-ee0a-48e2-8557-bdf2b6ce676e","172b353a-f111-4165-827d-1d7a99e6c1eb","84bff966-7caf-417a-9e7c-6155e01c71b9","9819cb3a-2fb2-4432-95d1-e693479a964a","9177c9a3-5569-4444-a633-030e562ef971","608e3091-e0e8-4ad6-af29-d395c230293d","d69c3367-ad5e-48e3-942c-3d724639b925","621e3cf4-0fba-42d1-8888-0a6c7029d3bf","714cd07a-ef49-415f-8ba7-7918cefa4d1b","8af650c0-42da-48f7-a4f8-cb47c0563a0a","cc9f8eca-0000-4654-9c80-7d87e490180a","54b92ffd-7e6d-49d7-88d3-99f4561b2feb","6a9bfb97-e240-4500-b648-95777892fa41","eb4e9a94-1371-40eb-b633-a51556a9673b","d00a5867-6fe0-486e-822d-05690397fb1a","dfb6b216-664c-4ff9-938b-7ac4fde7fcc4","2455e0c2-0223-45ff-9314-c8137bd75615","970a9f00-29d4-4a2f-876a-e0318aaa9e73","7cbcf73f-4507-4f16-afc7-089e10a8c260","23d813f0-6145-4617-b28e-bba2de9c2f70","b0942367-7285-459a-8476-0ba062107149","5d5191ff-9dc9-4e62-a6ae-d6662a82b60c","01e17a89-c752-477e-8f5c-177dfe3db2ec","2d7f92ca-0fcb-4238-b386-b63c60c453cc","1e5f8b63-ef0a-4252-8304-11a3c929eb84","4b64224a-a991-433a-bbfa-aee204fd8194","4d0d51cd-5d48-4dbc-94ba-4fb40e2b2897","7addedba-24c0-40b9-b922-aa8c01c54b08","4ca96e8c-fcc5-498a-aaa9-07fc7ff21b40","4902c4f6-8745-4b1b-a4ec-882ba3a7556d","dd8fbfd5-b220-46c3-9eaf-bb47bf441328","da8e4140-bf4b-468f-bb55-c819085c214b","fa79e755-9da0-480b-ab00-f05d22960d0b","dfd40fbe-f5dd-4b6d-8454-eb5ede91d2cf","2d7558bf-88df-4fa8-9bfd-5747be7fc810","13ca43eb-a76d-49d8-8b5a-19ab73082bac","bdf9fd54-7ea2-4e0f-baa2-b39850d36fb6","ab5408bf-4935-4bf3-b7d8-93578d86d701","58019c07-6208-4ad7-a658-cae5e5779808","1bea71e2-c643-4351-b28d-c2cd98c1f5a9","f248a891-d078-448a-8068-394b7daa80c3","eeaf3baa-4c26-45d6-8d89-b1ee6a57987a","cecf5a4f-bb81-499b-843e-80548d5072dc","4a0e26b7-a55c-497b-a7aa-3461f0ad396c","f4825dc2-5f07-49e4-97df-8081a8022301","5dfd68f9-b81a-4478-b96e-523bcfb9663d","76fc7303-5c75-48b2-ac8b-6b15c50b4871","1be888ef-0a2b-488d-bb7b-9a2722c14341","2aea0d35-10fd-45b1-b03f-5cbb7fce1b6f","231e71b0-cc8c-47b8-9a4f-c688599a9e6a","e8f35d16-e144-40e0-92de-62593a4d7761","828932c8-a77a-4b0e-bcff-d27dfed27709","5c36c914-ff0d-43cc-9b3d-c654c329b293","33e65cdd-1f9e-451a-a970-a85a9c78e885","b5016760-f481-4053-89ad-f9f841c5e516","869ac169-83bc-4d34-abe0-aa01fe2a7621","e8bdb294-385c-46c0-bfc9-86d0e39818f3","916cd2ab-3024-449d-92cc-26d176cb2260","0e3084f7-f910-4ce9-a0c3-510996587855","d8574b83-b80e-4c1b-9c9e-d55189433877","be625ed2-7ff3-4e70-8e98-a7eb5085d6c5","473cf8a1-1724-41a9-8954-2c3f871db86e","9d9560d3-ffd2-4951-beb7-2281d8dc8b49","f0789c07-c6c8-43ea-acbb-b51f0694c126","e1311c0f-5540-42b2-9580-2ef1f253218a","73a13bc8-3429-46d2-962c-f533783aaef6","6801d4c6-4458-43c9-8391-d635b6db41f0","d372490f-460a-44ad-b18e-2edf596db1ad","57bac420-37b7-4fcf-84ce-978d169968bf","05fe92e7-fc1d-497c-8242-2b759d2ed904","94a15135-7035-404d-8e22-3abbe270a3ba"]
    print(tasks)
    result = {}
    for task in tasks:
        try:
            result[task] = get_result(task)
        except Exception, e:
            result[task] = {"error": str(e)}
    return result

result = get_results()
print(result)
json.dump(result, open('/data/output', 'w'))


