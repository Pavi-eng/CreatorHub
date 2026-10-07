export const SECTIONS = {
  projects: {
    label: "Projects",
    fields: [
      { name: "title", label: "Title", required: true },
      { name: "description", label: "Description", type: "textarea" },
      { name: "techStack", label: "Tech stack (comma separated)" },
      { name: "projectUrl", label: "Live URL" },
      { name: "repoUrl", label: "Repository URL" },
      { name: "imageUrl", label: "Image URL" },
    ],
    columns: ["title", "techStack"],
  },
  skills: {
    label: "Skills",
    fields: [
      { name: "name", label: "Skill", required: true },
      { name: "level", label: "Level", type: "select", options: ["Beginner", "Intermediate", "Advanced", "Expert"] },
      { name: "category", label: "Category" },
    ],
    columns: ["name", "level", "category"],
  },
  certifications: {
    label: "Certifications",
    fields: [
      { name: "title", label: "Certification", required: true },
      { name: "issuer", label: "Issued by" },
      { name: "issueDate", label: "Issue date", type: "date" },
      { name: "credentialUrl", label: "Credential URL" },
    ],
    columns: ["title", "issuer", "issueDate"],
  },
  experiences: {
    label: "Experience",
    fields: [
      { name: "company", label: "Company", required: true },
      { name: "jobTitle", label: "Job title", required: true },
      { name: "startDate", label: "Start date", type: "date" },
      { name: "endDate", label: "End date (empty if current)", type: "date" },
      { name: "description", label: "Description", type: "textarea" },
    ],
    columns: ["company", "jobTitle", "startDate", "endDate"],
  },
  achievements: {
    label: "Achievements",
    fields: [
      { name: "title", label: "Title", required: true },
      { name: "description", label: "Description", type: "textarea" },
      { name: "achievedDate", label: "Date", type: "date" },
    ],
    columns: ["title", "achievedDate"],
  },
};