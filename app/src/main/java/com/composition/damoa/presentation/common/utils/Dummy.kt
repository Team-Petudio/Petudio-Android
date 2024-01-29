package com.composition.damoa.presentation.common.utils

import com.composition.damoa.R
import com.composition.damoa.data.model.PointChargeItem
import com.composition.damoa.presentation.screens.profileCreation.PetPhoto
import com.composition.damoa.presentation.screens.profileCreation.UploadedPetPhoto
import java.time.LocalDateTime

fun uploadedPetPhotos(): List<UploadedPetPhoto> =
    listOf(
        UploadedPetPhoto(
            "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
            "코코",
            LocalDateTime.now(),
        ),
        UploadedPetPhoto(
            "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
            "몽실이",
            LocalDateTime.now(),
        ),
        UploadedPetPhoto(
            "https://postfiles.pstatic.net/MjAyMTA4MjhfMjM1/MDAxNjMwMTUwOTQyODM0.PtRSphi1LaRrnLIco2ETUVdh_WNZVv4u8DF_JOzggP4g.FBvbfV6eJX7hvVn2fcjEe4rNPgKEbBgvRBTL0RyKDXEg.JPEG.oneulst1/18--46.jpg?type=w966",
            "구름이",
            LocalDateTime.now(),
        ),
        UploadedPetPhoto(
            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUSFRgSEhUYGRgYGBgaGBwYGBgYGBoYGhgZGhoYGhgcIS4lHB4rIRgYJjgmKy8xNTU1GiQ7QDs0Py40NTEBDAwMEA8QHhISHjErJCs0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDE0NDQ0NDQ0NDE0NDQxNDQ0NP/AABEIAOEA4QMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAAAQIDBAUGBwj/xAA6EAABAwIEAwYGAQMDBAMAAAABAAIRAyEEEjFBBVFxBiJhgaHwEzKRscHRQhRSciPh8QdikrIVJIL/xAAZAQEBAQEBAQAAAAAAAAAAAAAAAQIDBAX/xAAnEQEBAAICAgEDAwUAAAAAAAAAAQIRAyESMUFRYXETIpEEFDLB4f/aAAwDAQACEQMRAD8A7JCELi+6EIQoBCEIBCRCBUiWUIEQlSIpEiVIgRCEIEQhCASJUIGpE4pCqGlMcU4lMcgY4pjk5yYUEblC5TOULkEUoQhUdHKEiFkKhCEQIQhAIhCEAhCIQIkT2MLjABO9uX4V9tBtOXOBIIDRoSM0STa0GRPiqxnyTH8s1Ir2BotqS0iCLgjx5jfZR4/CGkQJkEaxHl9k8etk5cfLx+VRCEijoVIlSIEQEqQoAlNKCkKBpTHJxTCqGFNcnOTHII3KJykconII0IQg6JCEKBUIQiBIlQgRCAFbwDw12YgEaGSAYJEkA6qybrOefhjafw7DtqAl02MW6TB+oWu2iNALDTZLRLJ7kZf+0WB308k6tWa3U21nku0mo+XycuWeX+jHUjqWk+/VFOkHaieunRUq2NDyMjiRznXxCmwlR0Xa/Xwj7gptq45THdSPwgbdrQOcABQ4gAgNf9p96qxRL80vAa3Zouerj9LD1T8dWa1pJAJjug7nboPFRJnZZL2wquFzPDWAxHeMWG+umivf0bYgNFvC/UndWqFNtRocHFrgBmDXGAYuI057KuMQ34rqUyQBPPQHzFwpJp0/VuXU+EY4QCC7Ne5sBHSEhwzXF3dub20Fhpy3+qsOlpIFwRaE+gHaxYmP2tTGJc89btZGJ4c5jg0XnT/dUSutqiHNA1i58NI98lTx+EaWm3ePy7XWMsfo6cf9Vepk5wppUppEugNdJuAR3o8QonLm90sphTSnFMKKaUxye5RuVEblC9SuUTggjlCEIOiSoQoBCEIBDWk2FylUjKrGBrnAnvQYtA5grWM3WM8vGGtc6mQ4WI5hOp49wc3McpBAJAkZYJ212jqrPEcGKgDg+QdJzwRb5SxwgwdSHa+Szm9nwGPYyrUPxJdnmXs7sDK7ePK86GI6SXF5MuXHLuxXb2yw2epTqVntdTc5rjoHQ7LLIN72gXHqtDD8VpV3ZKRe/MCbtI+ua68ywvCv9TLTHxDMNDWEHX+06HqvXez/AAZmHa0uaDUI7ztYm5a3kP0ucyuV+zx7+VzhvDhSaCRLoufwOQV9ySU0ldPTNtt3SPCidhg4yVIFICi7sV24VrTmbYpxotOoG/gZMyZ8ypimyhuoKmHm7T5feFKxwAyjb7c08EKricMHhWLvfVPq1w0Zjc6ABV34zNIyGdrj8qb4bQBImFncU4gKdNz25RlaTJJBtqGiLnZaSaQVajcO4d8FzwAMzgDqZgnabHxVXFYJwBfEWzEHWTrAE2uuX4Pja+OxLK9QNy4drwxoGWXPAGe5uAW6bkBdVRpve9xqVQ0WAZcyBBAc60zOwWJJlPs9fFllP3M4phU+IIzGG5do5EKArlXvl3NmuTHJxTXIqJyicpXKJyCNCEIOhQhCgVCRKgkpU8xDZAnmnOxFKi6K9TI0C4JGWZuSSPlgN9Zskw1YsJcADa4PKU3jdSjWpOFVj2A3zZCe8YFoHe5R0XTH1uPJzZZb8YcKLWtJYe8+zXCe40iJbHyCNSPErlsbjf8A4ypQp4eo6oXAuqio8lxByBrg4nuTlfAiJBtzs9lsFiMM9uVzH03tdlkVP9NktnvOAAbazIk7QAVNhOBUjihUpsyuDv8AVzZSMxY13dA0OrT8vyWHPV3Y82dtm9duk4JgqTicR8FrX1IcSA2AYEkRaSZJOpJK3CVAKrWNkrLx/HqTJBJB/wAXR9YjdT04621jiQlZUlcfT4o/EOLcOC+NY0b1doOn6W5hKFYOGfLHgZhNr4yNkFJ8RU+I4+nhmGpVfDRy1J2a0blcq3ts6pJpUWFoP8ny7zy/Kfqqy7Q1JRK5jAdq2Pe2nXpmkXEBrg7M0k6B0gFvr5Loqz8rSeSLo9z0nxFnNx7SfmHmpm12u0KmyzR3EsK6pTc2m803EEBwiRbUSvHcVhX8Le5lV1V1I06gJlzmPcWEMbBJGt51EDReytcQNvfiuX7bcIdiaJDG5jIlt5ifmHiOiVHH9mMWMLSZUY4VK1YSSHFwDG5srWtEWAJmRObpA66jjKLqf9RUaS8ughoJMA7QJjU+F+qzBS4bgMuGeBmqtPfqZcwLQwHIQO4XZgRl3C0eEUa5pini6lMNa3O11N3eLZIDXtLQGkAtnKYvZI9OG8eqmr41tSA1jmtaLSADM9STbmqxWkxgyZGMDjlBcbQARIIPNZpWc529vFZrUNKY5OKa5ZdUTlE5SuUTkESEqEHQIQkUDkJAYUjL7D1/aJbpI/B56RObKTuDeOkftedcS7W1aefDAtq0ScjviAuLmEwWiCLa97aZi0rvsTiXspVGsptfLTAnLOxkkWgSVhYPh9BuCfSMPqPaXODmguY+BGQgSWtIEak6jVdJu+ni5JnbWzwvtDQxAIoOzTDQIMAgXbOki1vEQoOynDa1F+JfUFnYh4ad3Q93eF7N73odV51wniH9Hi2Vm0u4yWvtBcCCC4SLRYgEc9Jt6N2e4/UxzHOFNzW53EPBGQtzS0ZtSYgGBsbiQrMtuHnLO2xxWrUyk0wwmLZnGB5ASV5/huzON4lW/wDs1iyg0yRTAZOvdAvDtJJJXpOGw+c3OmttfCStAsgQAEjnaocK4TQwdMUsOzI0dSSebibk+JU7a4BuUmIcRsfJVXNdluIOscr81WXnX/UDjFd9fI2nnYwFrLjLmIGZ0ak+UDu768hwXNhWuqPJJeRadLgk/jyW/wBr5NfMDpERaL2sNiuexJeS3Mc3eBFoIjSSNeqKjxGILcWX1nEMeDF9BHd6Rp5L2vsTxj+pw7czg9zAGOcCHB0Cxkbx+9145WrP+XIwEnXKSSP/ANO0vHLqvUOyzDTogQAXAkgCBcnbwsERd43wKo4GphXSdSwmPJp59VjYPjT6D2067HMOwcCCY5SYK7fCWsd/vrfyKOKcMp4pnw6gkaiCQQRoRCml39SYLGMqt7rgfP8ASWuIFvp+liYfhNbDWD/iMBtIy1GjqLO8481qtqSzWbaGx9VR5L2q4aK+Ke+S5gIbldIgD5mg6gEyZ8d1pcO4rRqVadOoX5i8BuemHMdpLHOg5RA1HUzos7jXGG0HPY9jnVG6/wBvXNuD4BZWF4s57C57AXse14a2QCJlo9CFxmV2Y5Zbt+r0vjOJLmtqU6D2Fr8tQMyzGkFg+bvQJsfJNmVYq1GV2NfY5wDf+UCxI3O11XK3n7fT4OpowppTimOWHcxyhcpXKJyojQkQg6BAQllQOa1SNTGaT9Es+/wqzVqhiMl8rXDUyL+R2WY5tPE1WOfQp5i4ANLA7f5zEdd+qvESCq+DOR4cBBiJEc79dl0xvw8eevK/VzPaDh7XVXNAGVxcDFwHSQ5ruTpEx4yu54fhxSoMptEZWgRELExppuqNDmguFRr8xEtvOp3MjfcgrpqlQADnCkx8bXDk4/GloF3yi3vmrAIFp6/8qs18DXr72UrGSL73WnKnOqA6CfTznl4rM4i4hktjM7Uu0DQDoN9SY8b7qxiMS1vdB5Tvpz/SxOL45rGufUdpGVszFwAY3IkGecDZCYuD7TQXxBLjN9zp4TuOnILDdRqAZy2zYsYm4dJ57enmtHG8XpGoC8hjHS+5HeDTdsnR0GBzuFzmE7RjM5r4bTfAkT3I/kIEkn0MIv5bPCqHxXiQQCR1323PovW+E0O7G+ltnRAPmI9Oa807MYunVqh7YgE94xrl0MbmDpFl6fgrXabtFxzaTLfMGR5olX8O0PAmQS1p6ECDHK49FZ748ffqs6tnkPpmQM1vAmfIjSfBWaWJzaWO4Pu6Gj6lfNYjzVCs5oBDrKTHPf8AO3bXb/ZUqWKzznAPopa1I8v7c4YurMdEtuMw0MRAMWkcuhtedrhXAGOoMcPnjMSd5vkPgLXVztPwlpfmbOV0TGodK0OEMysDeRI/Sz497anXYwGEe0Gct4s2YbFrTc2jrdTVGwSFfw7Id4FQcQZBB8Psrl6erg5N3VUimuTiVG4rm9hjlE5SOKheVQ1CSUIOgQkQoHtdCVl1GnsJRKt022Kq16dgVoUdCdtPf1UWIp2gbELpHz+S/ucrjqPezD3eVpYDib3Q18mN/pH5TcRRLiQoMHUymw8ChvcaT+MBhDjcC8Hc7IPGPjOgPyg+PLVZPFcOHNNRm3pC8x47jKr6wpNc5ggRBIJF5NkZuMeo4vtNh6bXvzDIy3OXaDznbXVc/jqOKxYORrKeaC013inmF9GfOIm3dGqqdgODSXVnkhoqRTDtMwHeqidSA4NadJLjqAR2tRtLKe6DGsgEnxMi+6OmPH9XluN/6e8QuSKbzqA2o3fkHZQuVx2AqUHmnWY6m8ahwIMaSOYsbixXvr8HTyD4YLXBgILHOa6Df+Jv82/6WNxfhwrt+Hif9VgnK4tArMmO8x4AnbukEHdXbGXBL6v8vKOzfEDh6oJdDD83kCQfC4A817R2f4vnAOYG0tI3EmR9F5ZxLgRovdTMEtNiBZzSAWuA5EEHzWbQ4jWwjsrHWMGCTaDty3Sufj4zVfQtHiGV0Osdx9nDwhWqlRlQHLZ2vX9FeS4Lty17GfHBaQYBEmNI0Gklw30vrbocN2ha/KWm82I06ghNrMN+nWtxX8SZ9D0UL8HlGdnmNT66+iyMPjZM6krYdWhhcTFtdWztKm9lxsV34um8ZH2JaYka6GQNZmPFR0aeUkeIVHhLTUeS+Dlccu48lrkRJ8ff2Vi5a9Q19SPIhMxbswlRTKSbQouF8cpVQpjipKigcub6c7NcVC9SOUT1VNQmoQdGhIhRAgFCRBdw2I0bsp34gElZanwVAvfr3Y734Wsb8PLz8U/yhKlElxygnXQLMxuBqgl1Nh5kCDPlrK69oboI8klSmDyK3p5Zlpw/DcWXufSqWMxB1v8AaFzPa/gLz3mN7ze82wuN23FtPQL0fH8OD7wMw0P46KNlHMAKjATEGQD5hNN27jkeB1BSw9Fp1+GxzrXzVB8R0+b9OiTF44k2I157SjtVg30nGoyzI7xH8ItprC5WriZIAeNA6Jix0MHyUld5cdTt12P4gQGEf2M/9I/KfQ4oXi91yFfHFwbEWaAY5CVDS4reA4SfueSL1t0fGw14ZUGwcw9Gw4T/AOZ+i814iczjUizj3f8AEWB9J811XabHZKLaM99x7wkWa6Ad+Qjz8FynwXvIbEk2AbEnwt03Vjy813lZEDnOeQ0SdgIvPQLvOy3DXtaA87zGsE7Tup+znY3KA+peodANGDruV1xazDtyiC6LDl4kpUwnzRQYyjGYTPPlzKq8R4uXj4dMiNyPsFWxNZ7wSTeFSwTWtALiJ2Cy6adbwVvw2B7vmdOXeTH2VjF1YimPP371WMzid2+DYEbeSt4Z2czqSVdsWd7XKYUlRkAkBDREA73/AGjE4puWGnvfhGsMbllNRmvdKjcnFMJXN9HRjlC8qVxUTlVMlCRCDo0iVAEmFBIMO87fZOGFd4Dz/SvhwgRpAH0CYag/ub9Qt6jzfq5X0rNwJ3cPKSrFLDZRGaZN7fSb6ftIazBq4ep+wTmV2E/N6O/Ss1K555Z5T/gex/8AFwA27setyoyKs/OD76K00gixH5+hupIDdTc/ZXW3Pz11ZP4VmF51gqVwteyK1VrAXHb78lm1OIxdwAHhqtVmdo+P4M1cPUZTAJcxwaDu6LLy6p2WqloDyMwDswvGUHQuNoGl/IalemuxxDu5BGpE+oTMXhRUEth0kEibW0B8Bcxz9MxnLGx5Q3BfBu1jZmWyCDYGH/8AaSflkkgEkwmGtdzzEkwIIE2geGm3/K6btDw2rmJDhBJ7vdi2ljqd9dVhYfgtQjxjMZEnYwR4CdNtOarnpbwuG+JJaTLhlOdudsx/IyMsgROWL2W/2H7PsDC+oyKrXua52o2ILdoII0Vns9wd9OBUEZhMjnyPOx9mCNuvimUGGnRIDzfS19XGFK1Jai4jWNIZKZbJ1dyHTmsk07EknzNyeZUzKEXcS46yU+syBcXJ0UdN/DLrMMgA+JhTYDAseXSe8NpvHNWYDQXOE9Visc5z3VGCHA5m9eXQ6KWN8eUl7b4wABBtortAZNPYVbh+PbWYHCzv5NOoKtErO69X6WN7OfXcRBUBTiVGSjpjjMZqQhTCnEphKjRjlE5SOKieqGISIQdIhCFAhQgoRCIQnUqeYgD/AIHNFtkm13Al3zOJyjneT+h+laaS4zz0/ZUL3WDQIAED8nqq+Oq5WZBq7XwHLz+3VdZ1Hzsv3ZbkVOJY2SA2MosJm/j5rOr1Myixj46KOlUBU2nj9DKzCeo0VsVHBuam4gkXAPLmEMZmIVl9EDujlr47ppuX4UKmMa8ZajZ18ktLE0acOa0B2k2Gmg980tfC/X7qu3hrX7XRbMVutxMvEMbHvb6pmFo/yPqU+jhcttIWhSYBcgK6c8vsrnUe/NOFDMcx00HvkpcsmBqfTyV2lQ0Gwsq5WsniWH7kR8x9FTw2EyEGJC3MSzM/wG3RP/pu7ohtz+IwApvFSmIm8DnurjXyJWk7DZhO7Y+nuFXxWGym2h+6zY9PDzauqqkphTnNPJMK5vdCFMJTimFFMconKRyicqhiEIQdKhCRQKkQhAit4aGtnc+g9/hVqbJMHTfop3ibLWMcObLrxPFSJcdPcKlUcXTNyVJiH7cvuo2hW3dZxxmOFt+WZjmd4j31VFlMgzt9Fo4t0uULHwYge+arz96XuGskF/Kw/wAo/H5CXEMLT4z9tf0tHC4YCm20auPmJ/X0WfivmjkB9s33KukmR7hmbZOo0hYqqMUKbwHfK+QTygC/qtTDs70c9Oov78kjVqJ+FESJsZm8xyPvYJjx5rTyQD78ln5O8cx3OlrK2Odp+GZyV6m3KJUOHYD7KuVQAxGKpUm5r81b+DZR4Zt1fLTHkjNU3gNyuixsVWx1OG25yFeDM0KLEsmByQlY2YsOXbXyKjxOG/ky4Oqs49kEFNZYSpZt3w5bjdxlOUZWuaTKmog8xuqWIwbmmAJ+6xcbHtx58clJyicpqjSLER1UD1HUxCRCDp0iVCgRCFLh2Am+g1/AVTK6m6eylAnnfy2Q50An6KSq9V67tG8teq36jzY7yy7ROUTmEOlTsgm6c9tlMYc+WpIyarLyT9FJhqIc4A+fQa9Eldv1VnhzIBd/dYdN/X7K/Lh7i+1+wOtvIqhU75L9iTHgNlZrPysd0geevpKqMOy1EUMW3vgcgJ6m/wClf4Vi4im+0EZHcvA+CYKIcc3P7be/BPNNu/7RLW0+oIzHRU3APdM+qqteNCbeJVygGnRw+solq9hqYFym4mpmsNEjmEtgJKVEjVHNaoMyi6svgtULATqpc30RKqFxa5SPEwUVNRZRYh9lRn8TVeiczYRi3kpuEKjXwkLFYa4PGU67JX00Mpzb6Km1bEUxGV4kbHcLDxuGynu3G3PzW5iXk2hUSVm47dePmuP4YiFrfDb/AGhCz416f7nFpoSIWXpKAr2TKMu+/X3ZR4Sn/I9G9dz5e9FPqtYx5uXPd1Faq7LB8beJ9yfJVfFPxb8zmgaCY8dJPvwTXJlWuPHW9karBIIVYOhR1H3BHgmLn/UY7sR4imS6BrKuNAAAG1v90ymJlx6D8oqVMrSd9B1VccfSviXyfAW/ZUjaR+mqrsVum86LcZpG07e/RLknUD7KwHAiE1tM6hRlB8Ibe/wreHpHZOaI11VmkN0SpKLYU7GXkqLkpibSjNPJhMe9RPqQoH1UNJnvndVcQ9KxygrFF0pVym4QS4AblNrJcK/K4HxCLfToX4YZVSeIWhiHWnmFn1AqxFauIlZtUrQrGQs+rdGogv4oSyhQaiAhC4vrtBvyt/x/KUb/AOJ/9ShC6R4b7Zg+YdD+EIQsV68fk1yg5JEK4uPP8L2w8/uqfEPlb5oQtOEFPQdPwrGHQhanpirh/SBr9PyhCMJXKSnoPJCEEwT6n4/KEIyru0VdyEI0exQV0IQUK36/KQIQhW4dG/4hQ1fwhCrEUnaFUa2iEIqBCEIP/9k=",
            "얌비",
            LocalDateTime.now(),
        ),
        UploadedPetPhoto(
            "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
            "부나",
            LocalDateTime.now(),
        ),
    )

fun goodDogPhotoExamples(): List<PetPhoto> =
    listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example1,
            descRes = R.string.dog_photo_upload_good_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example2,
            descRes = R.string.dog_photo_upload_good_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example3,
            descRes = R.string.dog_photo_upload_good_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example4,
            descRes = R.string.dog_photo_upload_good_example_desc4,
        ),
    )

fun badDogPhotoExamples(): List<PetPhoto> =
    listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example1,
            descRes = R.string.dog_photo_upload_bad_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example2,
            descRes = R.string.dog_photo_upload_bad_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example3,
            descRes = R.string.dog_photo_upload_bad_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example4,
            descRes = R.string.dog_photo_upload_bad_example_desc4,
        ),
    )

fun pointChargeItems(): List<PointChargeItem> =
    listOf(
        PointChargeItem(
            point = 110,
            price = 1100,
        ),
        PointChargeItem(
            point = 220,
            price = 2200,
        ),
        PointChargeItem(
            point = 330,
            price = 3300,
        ),
        PointChargeItem(
            point = 440,
            price = 4400,
        ),
        PointChargeItem(
            point = 550,
            price = 5500,
        ),
        PointChargeItem(
            point = 660,
            price = 6600,
        ),
        PointChargeItem(
            point = 770,
            price = 7700,
        ),
        PointChargeItem(
            point = 880,
            price = 8800,
        ),
        PointChargeItem(
            point = 990,
            price = 9900,
        ),
        PointChargeItem(
            point = 1100,
            price = 11000,
        ),
    )
