using System.Collections.Generic;

namespace consts
{
    public class Consts
    {
        private static Dictionary<string, string> authenticationType = new Dictionary<string, string>();

        public static Dictionary<string, string> getAuthenticationType()
        {
            authenticationType.Add("anonymous_with_impersonation", "Anonymous with Impersonation");
            authenticationType.Add("basic_with_impersonation", "Conduit Authentication with Impersonation");
            authenticationType.Add("active_directory_with_impersonation", "Active Directory with Impersonation");
            authenticationType.Add("basic_pass_through", "User Credentials Pass Through");

            return authenticationType;
        }
    }
}