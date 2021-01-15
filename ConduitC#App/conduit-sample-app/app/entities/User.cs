namespace entity
{
    public class User
    {
        public User(string emailValue, string passwordValue)
        {
            email = emailValue;
            password = passwordValue;
        }
        public string email { get; set; }
        public string password { get; set; }

        public override string ToString()
        {
            return 
                "email='" + email + '\'' +
                "password=" + password ;
        }
    }

}