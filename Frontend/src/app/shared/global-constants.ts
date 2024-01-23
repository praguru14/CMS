// Name[a - zA - Z0 - 9] *

//     Email[A - Za - z0 -9._ % -] + @[A - Za - z0 -9._ % -] +\\.[a - z]{ 2, 3 }

// Contact Number ^ [e0 - 9]{ 10, 10 } $

export class GlobalConstants{
    public static genericError:string = "Something went wrong! Please try again later."

    public static nameRegex: string = "[a - zA - Z0 - 9]";

    public static emailRegex: string ="[A - Za - z0 -9._ % -] + @[A - Za - z0 -9._ % -] +\\.[a - z]{ 2, 3 }";

    public static contactNumberRegex: string ="[e0-9]{10,10}$";

    //Variable

    public static error:string="error";

}